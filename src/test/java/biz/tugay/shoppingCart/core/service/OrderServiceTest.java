package biz.tugay.shoppingCart.core.service;

import java.util.List;

import biz.tugay.shoppingCart.BaseIntegrationTest;
import biz.tugay.shoppingCart.core.entity.OrderItem;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import biz.tugay.shoppingCart.core.repository.OrderItemRepository;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.junit.Before;
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
  private ProductRepository productRepository;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  // Sample data
  private String shoppingCartId = "shopping-cart-id";

  private Product peperoni;

  private int peperoniCount = 12;

  @Before
  public void createSampleData() {
    peperoni = new Product();
    peperoni.setSku("product-sku");
    peperoni.setName("Peperoni Pizza");
    peperoni.setDescription("Good old peperoni pizza.");
    productRepository.save(peperoni);

    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, peperoni));
    shoppingCartProduct.setItemCount(peperoniCount);
    shoppingCartProductRepository.save(shoppingCartProduct);
  }

  @Test
  public void mustSubmitOrderSuccessfully() {
    // Given a shopping cart exists
    // When the shopping cart is submitted
    String orderId = orderService.submitOrder(shoppingCartId);

    // All shopping cart content must be available by orderId
    List<OrderItem> orderItems = orderItemRepository.findAllByOrderItemId_OrderId(orderId);
    assertThat(orderItems.size()).isEqualTo(1);

    // Contents of order must be correct
    OrderItem orderItem = orderItems.get(0);
    assertThat(orderItem.getOrderItemId().getProduct()).isEqualTo(peperoni);
    assertThat(orderItem.getOrderItemId().getOrderId()).isEqualTo(orderId);
    assertThat(orderItem.getItemCount()).isEqualTo(peperoniCount);

    // Shopping cart must now be empty
    assertThat(shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(shoppingCartId)).isEmpty();
  }
}
