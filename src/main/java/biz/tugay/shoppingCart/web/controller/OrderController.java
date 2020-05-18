package biz.tugay.shoppingCart.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import biz.tugay.shoppingCart.core.repository.OrderItemRepository;
import biz.tugay.shoppingCart.core.service.OrderService;
import biz.tugay.shoppingCart.web.RequestContext;
import biz.tugay.shoppingCart.web.dto.OrderItemDto;
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

  private OrderItemRepository orderItemRepository;

  @Autowired
  public OrderController(
      OrderService orderService,
      OrderItemRepository orderItemRepository)
  {
    this.orderService = orderService;
    this.orderItemRepository = orderItemRepository;
  }

  /**
   * @return All OrderItems belonging to an Order.
   */
  @GetMapping(value = "{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OrderItemDto> getOrderItemsForOrder(@PathVariable("orderId") String orderId) {
    return
        orderItemRepository.findAllById_OrderId(orderId).stream().map(OrderItemDto::new).collect(Collectors.toList());
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
