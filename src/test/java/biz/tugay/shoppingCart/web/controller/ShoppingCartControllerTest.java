package biz.tugay.shoppingCart.web.controller;

import java.util.Arrays;
import java.util.List;

import biz.tugay.shoppingCart.BaseIntegrationTest;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import biz.tugay.shoppingCart.web.dto.ShoppingCartProductDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static biz.tugay.shoppingCart.web.controller.ShoppingCartController.PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public class ShoppingCartControllerTest
    extends BaseIntegrationTest
{
  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  @Test
  public void mustGetShoppingCart() {
    // Given a product and product being in a shopping cart
    Product product = newPersistedProduct("product-sku", "product-name", "product-description");
    ShoppingCartProduct scp = newPersistedShoppingCartProduct("shopping-cart-id", product, 4);

    // When a request is made to get all products
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", "shopping-cart-id=".concat(scp.getCartId()));
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
    ShoppingCartProductDto[] body =
        restTemplate.exchange(PATH, GET, requestEntity, ShoppingCartProductDto[].class).getBody();

    // Response must be correct
    assertThat(body).isNotNull().hasSize(1);
    ShoppingCartProductDto scpDto =
        Arrays.stream(body).filter(dto -> dto.product.sku.equals(product.getSku())).findAny().get();
    assertThat(scpDto.count).isEqualTo(scp.getItemCount());
  }

  @Test
  public void mustRemoveItemFromShoppingCart() {
    // Given a product and product being in a shopping cart
    Product product = newPersistedProduct("product-sku", "product-name", "product-description");
    ShoppingCartProduct scp = newPersistedShoppingCartProduct("shopping-cart-id", product, 4);

    // When a request is made to end point
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", "shopping-cart-id=".concat(scp.getCartId()));
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
    HttpStatus statusCode =
        restTemplate.exchange(PATH + "/" + product.getSku() + "/-4", POST, requestEntity, Object.class).getStatusCode();

    assertThat(statusCode.value()).isEqualTo(HttpStatus.OK.value());

    List<ShoppingCartProduct> shoppingCart =
        shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(scp.getCartId());

    assertThat(shoppingCart).isEmpty();
  }
}
