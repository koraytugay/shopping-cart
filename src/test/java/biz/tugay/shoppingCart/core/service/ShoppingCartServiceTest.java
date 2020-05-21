package biz.tugay.shoppingCart.core.service;

import java.util.List;

import biz.tugay.shoppingCart.BaseIntegrationTest;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartServiceTest
    extends BaseIntegrationTest
{
  @Autowired
  private ShoppingCartService shoppingCartService;

  @Test
  public void mustGetShoppingCartContents() {
    // Given a product and product being in a shopping cart
    Product product = newPersistedProduct("product-sku", "product-name", "product-description");
    ShoppingCartProduct scp = newPersistedShoppingCartProduct("shopping-cart-id", product, 4);

    // When all contents is fetched
    List<ShoppingCartProduct> shoppingCartContents = shoppingCartService.getShoppingCartContents(scp.getCartId());

    // Product and its count must be correct
    assertThat(shoppingCartContents.size()).isEqualTo(1);

    ShoppingCartProduct shoppingCartProduct = shoppingCartContents.iterator().next();
    assertThat(shoppingCartProduct.getProduct()).isEqualTo(product);
    assertThat(shoppingCartProduct.getItemCount()).isEqualTo(scp.getItemCount());
  }

  @Test
  public void mustUpdateProductCountInShoppingCart() {
    // Given a product and product being in a shopping cart
    Product product = newPersistedProduct("product-sku", "product-name", "product-description");
    ShoppingCartProduct scp = newPersistedShoppingCartProduct("shopping-cart-id", product, 4);

    // When 4 more products added
    shoppingCartService.updateCartUpdateProductByItemCount(scp.getCartId(), product.getSku(), 4);

    // Product and its count must be correct
    List<ShoppingCartProduct> shoppingCartContents = shoppingCartService.getShoppingCartContents(scp.getCartId());

    assertThat(shoppingCartContents.size()).isEqualTo(1);

    ShoppingCartProduct shoppingCartProduct = shoppingCartContents.iterator().next();
    assertThat(shoppingCartProduct.getProduct()).isEqualTo(product);
    assertThat(shoppingCartProduct.getItemCount()).isEqualTo(8);
  }

  @Test
  public void mustInsertNewShoppingCartProductIfProductDoesNotExist() {
    // Given a product and product being in a shopping cart
    Product product = newPersistedProduct("product-sku", "product-name", "product-description");
    ShoppingCartProduct scp = newPersistedShoppingCartProduct("shopping-cart-id", product, 4);

    // And another product being available in database
    Product anotherProduct = newPersistedProduct("another-product-sku", "another-product-name", "no-description");

    // When an item that does not exist in this cart is added
    shoppingCartService.updateCartUpdateProductByItemCount(scp.getCartId(), anotherProduct.getSku(), 1);

    // Shopping cart must now have 2 items in it
    List<ShoppingCartProduct> shoppingCartContents = shoppingCartService.getShoppingCartContents(scp.getCartId());

    assertThat(shoppingCartContents.size()).isEqualTo(2);
  }

  @Test
  public void mustRemoveProductFromShoppingCartIfDecrementEqualsExisting() {
    // Given a product and product being in a shopping cart
    Product product = newPersistedProduct("product-sku", "product-name", "product-description");
    ShoppingCartProduct scp = newPersistedShoppingCartProduct("shopping-cart-id", product, 4);

    // When negative number of same product is added (i.e. product is removed from the cart)
    shoppingCartService.updateCartUpdateProductByItemCount(scp.getCartId(), product.getSku(), -scp.getItemCount());

    // Shopping cart must now be empty
    List<ShoppingCartProduct> shoppingCartContents = shoppingCartService.getShoppingCartContents(scp.getCartId());

    assertThat(shoppingCartContents).isEmpty();
  }
}
