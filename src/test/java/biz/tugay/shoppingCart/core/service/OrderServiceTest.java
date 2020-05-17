package biz.tugay.shoppingCart.core.service;

import java.util.List;

import biz.tugay.shoppingCart.core.entity.OrderItem;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
import biz.tugay.shoppingCart.core.repository.OrderItemRepository;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest
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
  private final String peperoniSku = "product-sku";

  private final int peperoniCount = 12;

  private final String shoppingCartId = "shopping-cart-id";

  @Before
  public void beforeTest() {
    Product product = new Product();
    product.setSku(peperoniSku);
    product.setName("Peperoni Pizza");
    product.setDescription("Good old peperoni pizza.");
    productRepository.save(product);

    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, peperoniSku));
    shoppingCartProduct.setItemCount(peperoniCount);
    shoppingCartProductRepository.save(shoppingCartProduct);
  }

  @After
  public void afterTest() {
    productRepository.deleteAll();
    shoppingCartProductRepository.deleteAll();
  }

  @Test
  public void mustSubmitOrderSuccessfully() {
    String orderId = orderService.submitOrder(shoppingCartId);

    List<OrderItem> orderItems = orderItemRepository.findAllByOrderItemId_OrderId(orderId);
    assertThat(orderItems.size()).isEqualTo(1);

    OrderItem orderItem = orderItems.get(0);
    assertThat(orderItem.getOrderItemId().getSku()).isEqualTo(peperoniSku);
    assertThat(orderItem.getOrderItemId().getOrderId()).isEqualTo(orderId);
    assertThat(orderItem.getItemCount()).isEqualTo(peperoniCount);

    assertThat(shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(shoppingCartId)).isEmpty();
  }
}
