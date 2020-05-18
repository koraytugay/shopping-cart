package biz.tugay.shoppingCart.core.service;

import java.util.List;

import biz.tugay.shoppingCart.BaseIntegrationTest;
import biz.tugay.shoppingCart.core.entity.OrderItem;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.repository.OrderItemRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest
    extends BaseIntegrationTest
{
  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  @Test
  public void mustSubmitOrderSuccessfully() {
    // Given a product and product being in a shopping cart
    Product product = newPersistedProduct("product-sku", "product-name", "product-description");
    ShoppingCartProduct scp = newPersistedShoppingCartProduct("shopping-cart-id", product, 4);

    // When the shopping cart is submitted
    String orderId = orderService.submitOrder(scp.getCartId());

    // All shopping cart content must be available by orderId
    List<OrderItem> orderItems = orderItemRepository.findAllByOrderItemId_OrderId(orderId);
    assertThat(orderItems.size()).isEqualTo(1);

    OrderItem orderItem = orderItems.get(0);
    assertThat(orderItem.getOrderItemId().getProduct()).isEqualTo(product);
    assertThat(orderItem.getOrderItemId().getOrderId()).isEqualTo(orderId);
    assertThat(orderItem.getItemCount()).isEqualTo(scp.getItemCount());

    // Shopping cart must now be empty
    assertThat(shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(scp.getCartId())).isEmpty();
  }
}
