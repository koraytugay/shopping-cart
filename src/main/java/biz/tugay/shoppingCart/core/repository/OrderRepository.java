package biz.tugay.shoppingCart.core.repository;

import java.util.List;

import biz.tugay.shoppingCart.core.entity.OrderHistory;
import biz.tugay.shoppingCart.core.entity.compositeKey.OrderIdProductSku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
    extends JpaRepository<OrderHistory, OrderIdProductSku>
{
  /**
   * @return All OrderHistory items by orderId.
   */
  List<OrderHistory> findAllByOrderIdProductSku_OrderId(String orderId);
}
