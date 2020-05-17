package biz.tugay.shoppingCart.core.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import biz.tugay.shoppingCart.core.entity.OrderHistory;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.OrderIdProductSku;
import biz.tugay.shoppingCart.core.repository.OrderRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService
{
  private ShoppingCartProductRepository shoppingCartProductRepository;

  private OrderRepository orderRepository;

  @Autowired
  public OrderService(
      ShoppingCartProductRepository shoppingCartProductRepository,
      OrderRepository orderRepository
  )
  {
    this.shoppingCartProductRepository = shoppingCartProductRepository;
    this.orderRepository = orderRepository;
  }

  /**
   * Submits the contents of the shopping cart. Returns an order id that can be later used
   * to reference the submitted order.
   */
  public String submitOrder(String shoppingCartId) {
    List<ShoppingCartProduct> shoppingCartProducts =
        shoppingCartProductRepository.findAllByCartIdProductSku_CartId(shoppingCartId);

    // Generate a random orderId for tracking purposes
    String orderId = UUID.randomUUID().toString();

    List<OrderHistory> orders = shoppingCartProducts.stream()
        .map(shoppingCartProduct -> {
          String sku = shoppingCartProduct.getCartIdProductSku().getSku();
          long itemCount = shoppingCartProduct.getItemCount();
          OrderIdProductSku orderKey = new OrderIdProductSku(orderId, sku);
          return new OrderHistory(orderKey, itemCount);
        })
        .collect(Collectors.toList());

    orderRepository.saveAll(orders);
    shoppingCartProductRepository.deleteAll(shoppingCartProducts);

    return orderId;
  }
}
