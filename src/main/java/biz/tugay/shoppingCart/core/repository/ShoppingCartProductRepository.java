package biz.tugay.shoppingCart.core.repository;

import java.util.List;

import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.CartIdProductSku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartProductRepository
    extends JpaRepository<ShoppingCartProduct, CartIdProductSku>
{
  /**
   * @return All products together with their counts in this cart.
   */
  List<ShoppingCartProduct> findAllByCartIdProductSku_CartId(String cartId);

  /**
   * @return ShoppingCartProduct for a product in a cart identified by key CartIdProductSku.
   */
  ShoppingCartProduct findByCartIdProductSku(CartIdProductSku cartIdProductSku);
}
