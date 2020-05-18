package biz.tugay.shoppingCart.core.service;

import java.util.Map;

import biz.tugay.shoppingCart.BaseIntegrationTest;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartServiceTest
    extends BaseIntegrationTest
{
  @Autowired
  private ShoppingCartService shoppingCartService;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  @Autowired
  private ProductRepository productRepository;

  // Sample data
  private Product peperoni;

  private String peperoniSku = "peperoni-sku", hawaiianSku = "hawaiian-sku";

  private int peperoniCount = 42;

  private String shoppingCartId = "shopping-cart-id";

  @Before
  public void createSampleData() {
    peperoni = new Product();
    peperoni.setSku(peperoniSku);
    peperoni.setName("Peperoni");
    peperoni.setDescription("");
    productRepository.save(peperoni);

    Product hawaiian = new Product();
    hawaiian.setSku(hawaiianSku);
    hawaiian.setName("Hawaiian");
    hawaiian.setDescription("");
    productRepository.save(hawaiian);

    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, peperoni));
    shoppingCartProduct.setItemCount(peperoniCount);
    shoppingCartProductRepository.save(shoppingCartProduct);
  }

  @Test
  public void mustGetShoppingCartContents() {
    // Given a shopping cart exists

    // When all contents is fetched
    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);

    // Product and its count must be correct
    assertThat(shoppingCartContents.size()).isEqualTo(1);
    Product product = shoppingCartContents.keySet().iterator().next();
    assertThat(product).isEqualTo(peperoni);
    assertThat(shoppingCartContents.get(product)).isEqualTo(peperoniCount);
  }

  @Test
  public void mustUpdateProductCountInShoppingCart() {
    // Given a shopping cart exists

    // When 42 more peperoni pizzas are added
    shoppingCartService.updateCartUpdateProductByItemCount(shoppingCartId, peperoniSku, peperoniCount);

    // Product and its count must be correct
    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);
    assertThat(shoppingCartContents.size()).isEqualTo(1);
    Product product = shoppingCartContents.keySet().iterator().next();
    assertThat(product).isEqualTo(peperoni);
    assertThat(shoppingCartContents.get(product)).isEqualTo(peperoniCount * 2);
  }

  @Test
  public void mustInsertNewShoppingCartProductIfProductDoesNotExist() {
    // Given a shopping cart exists

    // When an item that does not exist in this cart is added
    shoppingCartService.updateCartUpdateProductByItemCount(shoppingCartId, hawaiianSku, 1);

    // Shopping cart must now have 2 items in it
    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);
    assertThat(shoppingCartContents.size()).isEqualTo(2);
  }

  @Test
  public void mustRemoveProductFromShoppingCartIfDecrementEqualsExisting() {
    // Given a shopping cart exists and a product exists

    // When negative number of same product is added (i.e. product is removed from the cart)
    shoppingCartService.updateCartUpdateProductByItemCount(shoppingCartId, peperoniSku, -peperoniCount);

    // Shopping cart must now be empty
    Map<Product, Integer> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);
    assertThat(shoppingCartContents.keySet()).isEmpty();
  }
}
