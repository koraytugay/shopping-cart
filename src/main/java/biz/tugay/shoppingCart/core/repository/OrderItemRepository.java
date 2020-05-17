package biz.tugay.shoppingCart.core.repository;

import java.util.List;

import biz.tugay.shoppingCart.core.entity.OrderItem;
import biz.tugay.shoppingCart.core.entity.compositeKey.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository
    extends JpaRepository<OrderItem, OrderItemId>
{
  /**
   * @return All OrderItems referenced by the <code>orderId</code>.
   */
  List<OrderItem> findAllByOrderItemId_OrderId(String orderId);
}
