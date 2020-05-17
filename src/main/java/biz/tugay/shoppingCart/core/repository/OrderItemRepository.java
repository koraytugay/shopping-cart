package biz.tugay.shoppingCart.core.repository;

import java.util.List;

import biz.tugay.shoppingCart.core.entity.OrderItem;
import biz.tugay.shoppingCart.core.entity.compositeKey.OrderIdProductSku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository
    extends JpaRepository<OrderItem, OrderIdProductSku>
{
  /**
   * @return All OrderItems referenced by the @param orderId.
   */
  List<OrderItem> findAllByOrderIdProductSku_OrderId(String orderId);
}
