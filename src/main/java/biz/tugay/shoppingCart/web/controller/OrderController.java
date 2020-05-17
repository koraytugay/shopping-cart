package biz.tugay.shoppingCart.web.controller;

import java.util.ArrayList;
import java.util.List;

import biz.tugay.shoppingCart.core.entity.OrderHistory;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.repository.OrderRepository;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.service.OrderService;
import biz.tugay.shoppingCart.web.RequestContext;
import biz.tugay.shoppingCart.web.dto.OrderHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController
{
  private OrderService orderService;

  private OrderRepository orderRepository;

  private ProductRepository productRepository;

  @Autowired
  public OrderController(
      OrderService orderService,
      OrderRepository orderRepository,
      ProductRepository productRepository)
  {
    this.orderService = orderService;
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  /**
   * @return the details of a previous order.
   */
  @GetMapping(value = "{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OrderHistoryDto> getOrderDetails(@PathVariable("orderId") String orderId) {
    List<OrderHistory> orderHistoryList = orderRepository.findAllByOrderIdProductSku_OrderId(orderId);

    List<OrderHistoryDto> orders = new ArrayList<>();
    for (OrderHistory orderHistory : orderHistoryList) {
      Product product = productRepository.findDistinctBySku(orderHistory.getOrderIdProductSku().getSku());
      orders.add(new OrderHistoryDto(product.getName(), orderHistory.getItemCount()));
    }

    return orders;
  }

  /**
   * @return A unique ID that can be used to fetch the details of the submitted order.
   */
  @PostMapping
  public String submitOrder() {
    String shoppingCartId = RequestContext.getShoppingCartId();
    return orderService.submitOrder(shoppingCartId);
  }
}
