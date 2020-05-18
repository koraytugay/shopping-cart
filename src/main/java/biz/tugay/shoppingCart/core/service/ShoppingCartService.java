package biz.tugay.shoppingCart.core.service;

import java.util.HashMap;
import java.util.Map;

import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService
{
  private ShoppingCartProductRepository shoppingCartProductRepository;

  @Autowired
  public ShoppingCartService(ShoppingCartProductRepository shoppingCartProductRepository) {
    this.shoppingCartProductRepository = shoppingCartProductRepository;
  }

  /**
   * @return A Map of Products and the number of items in the cart identified by <code>shoppingCartId</code>.
   */
  public Map<Product, Integer> getShoppingCartContents(String shoppingCartId) {
    HashMap<Product, Integer> shoppingCartContents = new HashMap<>();

    shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(shoppingCartId)
        .forEach(scp -> shoppingCartContents.put(scp.getProduct(), scp.getItemCount()));

    return shoppingCartContents;
  }

  /**
   * Adds or removes a number of products identified by sku to the cart identified by cartId. This method accepts
   * positive and negative numbers, where positive numbers mean increment the product in the cart and negative numbers
   * mean decrement the amount of this product in the cart.
   * <br/> <br/>
   * When incrementing, if item does not already exists, it creates one first, i.e. item does not need to exist already.
   * When decrementing, if there are x numbers of items and <code>itemCount</code> is less then -x,
   * item is removed from the cart.
   */
  public void updateCartUpdateProductByItemCount(String cartId, String sku, int itemCount) {
    // Find the product in the shopping cart, or create a new one if it does not exist.
    ShoppingCartProductId shoppingCartProductId = new ShoppingCartProductId(cartId, new Product(sku));
    ShoppingCartProduct shoppingCartProduct =
        shoppingCartProductRepository.findByShoppingCartProductId(shoppingCartProductId);

    if (shoppingCartProduct == null) {
      // One cannot decrement count / remove product from a cart if it does not already exist.
      if (itemCount < 0) {
        return;
      }
      shoppingCartProduct = newShoppingCartProduct(cartId, sku);
    }

    // Update the product count in the cart
    shoppingCartProduct.setItemCount(shoppingCartProduct.getItemCount() + itemCount);

    // Save if we have a positive number of items.
    // Remove product from cart otherwise.
    if (shoppingCartProduct.getItemCount() > 0) {
      shoppingCartProductRepository.save(shoppingCartProduct);
    }
    else {
      shoppingCartProductRepository.delete(shoppingCartProduct);
    }
  }

  private ShoppingCartProduct newShoppingCartProduct(String cartId, String sku) {
    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();

    shoppingCartProduct.getShoppingCartProductId().setCartId(cartId);
    shoppingCartProduct.getShoppingCartProductId().setProduct(new Product(sku));
    shoppingCartProduct.setItemCount(0);

    return shoppingCartProduct;
  }
}
