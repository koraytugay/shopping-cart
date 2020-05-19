package biz.tugay.shoppingCart.core.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import biz.tugay.shoppingCart.core.entity.OrderItem;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.OrderItemId;
import biz.tugay.shoppingCart.core.repository.OrderItemRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService
{
  private ShoppingCartProductRepository shoppingCartProductRepository;

  private OrderItemRepository orderItemRepository;

  @Autowired
  public OrderService(
      ShoppingCartProductRepository shoppingCartProductRepository,
      OrderItemRepository orderItemRepository)
  {
    this.shoppingCartProductRepository = shoppingCartProductRepository;
    this.orderItemRepository = orderItemRepository;
  }

  /**
   * Submits the contents of the shopping cart.
   *
   * @param shoppingCartId Shopping Cart in the database to be submitted.
   * @return The submitted orders order id, that can be later used to reference the submitted order.
   */
  @Transactional
  public String submitOrder(String shoppingCartId) {
    List<ShoppingCartProduct> shoppingCartProducts =
        shoppingCartProductRepository.findAllById_CartId(shoppingCartId);

    // Generate a random id that will represent this order.
    String orderId = UUID.randomUUID().toString();

    // Put contents of the shopping cart items into the order in the form of OrderItem.
    List<OrderItem> orderItems = shoppingCartProducts.stream().map(shoppingCartProduct -> {
      OrderItemId orderItemKey = new OrderItemId(orderId, shoppingCartProduct.getProduct());
      return new OrderItem(orderItemKey, shoppingCartProduct.getItemCount());
    }).collect(Collectors.toList());

    // Save Order, clear shopping cart.
    // Must be atomic, hence the @transactional
    orderItemRepository.saveAll(orderItems);
    shoppingCartProductRepository.deleteAll(shoppingCartProducts);

    return orderId;
  }
}
