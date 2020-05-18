package biz.tugay.shoppingCart.core.repository;

import java.util.List;

import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartProductRepository
    extends JpaRepository<ShoppingCartProduct, ShoppingCartProductId>
{
  /**
   * @return All products together with their counts in this cart.
   */
  List<ShoppingCartProduct> findAllById_CartId(String cartId);
}
