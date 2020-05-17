package biz.tugay.shoppingCart.web.controller;

import java.util.Arrays;
import java.util.List;

import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import biz.tugay.shoppingCart.web.dto.ShoppingCartProductDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@SuppressWarnings({"ConstantConditions", "OptionalGetWithoutIsPresent"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerTest
{
  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  private final String shoppingCartId = "my-shopping-cart-id";

  // Sample data
  private Product product_a;

  private Product product_b;

  // Sample data
  // We have 2 products in our shopping cart
  @Before
  public void beforeTest() {
    product_a = new Product();
    product_a.setSku("1234");
    product_a.setName("product-a");
    product_a.setDescription("product-a-description");
    productRepository.save(product_a);

    product_b = new Product();
    product_b.setSku("1235");
    product_b.setName("product-b");
    product_b.setDescription("product-b-description");
    productRepository.save(product_b);

    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, product_a.getSku()));
    shoppingCartProduct.setItemCount(10);
    shoppingCartProductRepository.save(shoppingCartProduct);

    shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, product_b.getSku()));
    shoppingCartProduct.setItemCount(12);
    shoppingCartProductRepository.save(shoppingCartProduct);
  }

  @After
  public void afterTest() {
    productRepository.deleteAll();
    shoppingCartProductRepository.deleteAll();
  }

  @Test
  public void mustGetShoppingCart() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", "shopping-cart-id=".concat(shoppingCartId));
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ShoppingCartProductDto[] body =
        restTemplate.exchange("/api/shoppingCart", GET, requestEntity, ShoppingCartProductDto[].class).getBody();

    assertThat(body.length).isEqualTo(2);

    ShoppingCartProductDto shoppingCartProductDto = Arrays.stream(body)
        .filter(dto -> dto.product.sku.equals(product_a.getSku())).findAny().get();
    assertThat(shoppingCartProductDto.count).isEqualTo(10);

    shoppingCartProductDto = Arrays.stream(body)
        .filter(dto -> dto.product.sku.equals(product_b.getSku())).findAny().get();
    assertThat(shoppingCartProductDto.count).isEqualTo(12);
  }

  @Test
  public void mustRemoveItemFromShoppingCart() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", "shopping-cart-id=".concat(shoppingCartId));
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    HttpStatus statusCode =
        restTemplate.exchange("/api/shoppingCart/".concat(product_a.getSku()).concat("/-10"), POST,
            requestEntity, Object.class).getStatusCode();

    assertThat(statusCode.value()).isEqualTo(HttpStatus.OK.value());

    List<ShoppingCartProduct> shoppingCart =
        shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(shoppingCartId);

    assertThat(shoppingCart.size()).isEqualTo(1);

    long productACount = shoppingCart.stream()
        .filter(scp -> scp.getShoppingCartProductId().getSku().equals(product_a.getSku()))
        .count();

    assertThat(productACount).isEqualTo(0);

    long productBCount = shoppingCart.stream()
        .filter(scp -> scp.getShoppingCartProductId().getSku().equals(product_b.getSku()))
        .count();

    assertThat(productBCount).isEqualTo(1);
  }
}
