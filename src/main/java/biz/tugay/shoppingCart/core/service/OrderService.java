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
  public String submitOrder(String shoppingCartId) {
    List<ShoppingCartProduct> shoppingCartProducts =
        shoppingCartProductRepository.findAllByShoppingCartProductId_CartId(shoppingCartId);

    // Generate a random id for tracking purposes
    String orderId = UUID.randomUUID().toString();

    List<OrderItem> orderItems = shoppingCartProducts.stream()
        .map(shoppingCartProduct -> {
          String sku = shoppingCartProduct.getShoppingCartProductId().getSku();
          int itemCount = shoppingCartProduct.getItemCount();
          OrderItemId orderItemKey = new OrderItemId(orderId, sku);
          return new OrderItem(orderItemKey, itemCount);
        })
        .collect(Collectors.toList());

    orderItemRepository.saveAll(orderItems);
    shoppingCartProductRepository.deleteAll(shoppingCartProducts);

    return orderId;
  }
}
