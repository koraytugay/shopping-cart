package biz.tugay.shoppingCart.web.controller;

import java.util.Arrays;
import java.util.List;

import biz.tugay.shoppingCart.BaseIntegrationTest;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import biz.tugay.shoppingCart.web.dto.ShoppingCartProductDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public class ShoppingCartControllerTest
    extends BaseIntegrationTest
{
  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  // Sample data
  private String shoppingCartId = "my-shopping-cart-id";

  private Product peperoni, hawaii;

  private int peperoniCount = 10, hawaiiCount = 12;

  @Before
  public void createSampleData() {
    peperoni = new Product();
    peperoni.setSku("peperoni-sku");
    peperoni.setName("peperoni");
    peperoni.setDescription("peperoni-description");
    productRepository.save(peperoni);

    hawaii = new Product();
    hawaii.setSku("hawaii-sku");
    hawaii.setName("hawaii");
    hawaii.setDescription("hawaii-description");
    productRepository.save(hawaii);

    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, peperoni));
    shoppingCartProduct.setItemCount(peperoniCount);
    shoppingCartProductRepository.save(shoppingCartProduct);

    shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, hawaii));
    shoppingCartProduct.setItemCount(hawaiiCount);
    shoppingCartProductRepository.save(shoppingCartProduct);
  }

  @Test
  public void mustGetShoppingCart() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", "shopping-cart-id=".concat(shoppingCartId));
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    ShoppingCartProductDto[] body =
        restTemplate.exchange("/api/shoppingCart", GET, requestEntity, ShoppingCartProductDto[].class).getBody();

    assertThat(body).isNotNull().hasSize(2);

    ShoppingCartProductDto scpDto;

    scpDto = Arrays.stream(body).filter(dto -> dto.product.sku.equals(peperoni.getSku())).findAny().get();
    assertThat(scpDto.count).isEqualTo(peperoniCount);

    scpDto = Arrays.stream(body).filter(dto -> dto.product.sku.equals(hawaii.getSku())).findAny().get();
    assertThat(scpDto.count).isEqualTo(hawaiiCount);
  }

  @Test
  public void mustRemoveItemFromShoppingCart() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", "shopping-cart-id=".concat(shoppingCartId));
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    HttpStatus statusCode =
        restTemplate.exchange("/api/shoppingCart/".concat(peperoni.getSku()).concat("/-10"), POST,
            requestEntity, Object.class).getStatusCode();

    assertThat(statusCode.value()).isEqualTo(HttpStatus.OK.value());

    List<ShoppingCartProduct> shoppingCart =
        shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(shoppingCartId);

    assertThat(shoppingCart.size()).isEqualTo(1);

    long peperoniCount = shoppingCart.stream().filter(scp -> scp.getProduct().equals(peperoni)).count();
    assertThat(peperoniCount).isEqualTo(0);

    long hawaiiCount = shoppingCart.stream().filter(scp -> scp.getProduct().equals(hawaii)).count();
    assertThat(hawaiiCount).isEqualTo(1);
  }
}
