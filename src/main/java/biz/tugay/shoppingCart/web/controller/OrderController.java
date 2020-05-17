package biz.tugay.shoppingCart.web.controller;

import java.util.ArrayList;
import java.util.List;

import biz.tugay.shoppingCart.core.entity.OrderItem;
import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.repository.OrderItemRepository;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
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

  private ProductRepository productRepository;

  @Autowired
  public OrderController(
      OrderService orderService,
      OrderItemRepository orderItemRepository,
      ProductRepository productRepository)
  {
    this.orderService = orderService;
    this.orderItemRepository = orderItemRepository;
    this.productRepository = productRepository;
  }

  /**
   * @return the details of an existing order by `orderId`.
   */
  @GetMapping(value = "{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OrderItemDto> getOrderItemsForOrder(@PathVariable("orderId") String orderId) {
    List<OrderItem> orderItems = orderItemRepository.findAllByOrderIdProductSku_OrderId(orderId);

    List<OrderItemDto> orderItemDtos = new ArrayList<>();
    for (OrderItem orderItem : orderItems) {
      Product product = productRepository.findDistinctBySku(orderItem.getOrderIdProductSku().getSku());
      orderItemDtos.add(new OrderItemDto(product.getName(), orderItem.getItemCount()));
    }

    return orderItemDtos;
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
