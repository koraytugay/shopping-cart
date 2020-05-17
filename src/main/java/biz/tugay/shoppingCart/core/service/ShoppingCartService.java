package biz.tugay.shoppingCart.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.CartIdProductSku;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService
{
  private ShoppingCartProductRepository shoppingCartProductRepository;

  private ProductRepository productRepository;

  @Autowired
  public ShoppingCartService(
      ShoppingCartProductRepository shoppingCartProductRepository,
      ProductRepository productRepository)
  {
    this.shoppingCartProductRepository = shoppingCartProductRepository;
    this.productRepository = productRepository;
  }

  /**
   * @return A Map of Products and the number of items in the cart identified by <code>shoppingCartId</code>.
   */
  public Map<Product, Integer> getShoppingCartContents(String shoppingCartId) {
    HashMap<Product, Integer> shoppingCartContents = new HashMap<>();

    List<ShoppingCartProduct> products = shoppingCartProductRepository.findAllByCartIdProductSku_CartId(shoppingCartId);
    for (ShoppingCartProduct shoppingCartProduct : products) {
      String sku = shoppingCartProduct.getCartIdProductSku().getSku();
      Product product = productRepository.findDistinctBySku(sku);

      shoppingCartContents.put(product, shoppingCartProduct.getItemCount());
    }

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
    // Find the product in the shopping cart, or create a new one if it does not exist
    ShoppingCartProduct scp = shoppingCartProductRepository.findByCartIdProductSku(new CartIdProductSku(cartId, sku));
    if (scp == null) {
      scp = newShoppingCartProduct(cartId, sku);
    }

    // Update the product count in the cart
    scp.setItemCount(scp.getItemCount() + itemCount);

    // Save if we have a positive number of items, remove product from cart otherwise
    if (scp.getItemCount() > 0) {
      shoppingCartProductRepository.save(scp);
    }
    else {
      shoppingCartProductRepository.delete(scp);
    }
  }

  private ShoppingCartProduct newShoppingCartProduct(String cartId, String sku) {
    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();

    shoppingCartProduct.getCartIdProductSku().setCartId(cartId);
    shoppingCartProduct.getCartIdProductSku().setSku(sku);
    shoppingCartProduct.setItemCount(0);

    return shoppingCartProduct;
  }
}
