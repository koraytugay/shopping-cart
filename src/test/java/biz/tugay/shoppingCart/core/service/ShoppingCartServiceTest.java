package biz.tugay.shoppingCart.core.service;

import java.util.Map;

import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartServiceTest
{
  @Autowired
  private ShoppingCartService shoppingCartService;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  @Autowired
  private ProductRepository productRepository;

  // Sample data
  private final String peperoniSku = "product-sku", hawaiianSku = "hawaiian-sku";

  private final int peperoniCount = 42;

  private final String shoppingCartId = "shopping-cart-id";

  /**
   * Add sample data used by tests
   */
  @Before
  public void beforeTest() {
    Product product = new Product();
    product.setSku(peperoniSku);
    product.setName("Peperoni Pizza");
    product.setDescription("Good old peperoni pizza.");
    productRepository.save(product);

    product = new Product();
    product.setSku(hawaiianSku);
    product.setName("Hawaiian Pizza");
    product.setDescription("Pizza with pineapple.");
    productRepository.save(product);

    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, peperoniSku));
    shoppingCartProduct.setItemCount(peperoniCount);
    shoppingCartProductRepository.save(shoppingCartProduct);
  }

  /**
   * Clear state
   */
  @After
  public void afterTest() {
    productRepository.deleteAll();
    shoppingCartProductRepository.deleteAll();
  }

  @Test
  public void mustGetShoppingCartContents() {
    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);
    assertThat(shoppingCartContents.size()).isEqualTo(1);
    assertThat(shoppingCartContents.get(shoppingCartContents.keySet().iterator().next())).isEqualTo(peperoniCount);
  }

  @Test
  public void mustUpdateProductCountInShoppingCart() {
    // Add 42 more peperoni pizzas
    shoppingCartService.updateCartUpdateProductByItemCount(shoppingCartId, peperoniSku, peperoniCount);

    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);
    assertThat(shoppingCartContents.size()).isEqualTo(1);
    assertThat(shoppingCartContents.get(shoppingCartContents.keySet().iterator().next())).isEqualTo(peperoniCount * 2);
  }

  @Test
  public void mustInsertNewShoppingCartProductIfProductDoesNotExist() {
    shoppingCartService.updateCartUpdateProductByItemCount(shoppingCartId, hawaiianSku, 1);

    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);
    assertThat(shoppingCartContents.size()).isEqualTo(2);
  }

  @Test
  public void mustRemoveProductFromShoppingCartIfDecrementEqualsExisting() {
    shoppingCartService.updateCartUpdateProductByItemCount(shoppingCartId, peperoniSku, -peperoniCount);
    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);
    assertThat(shoppingCartContents.keySet()).isEmpty();
  }
}
