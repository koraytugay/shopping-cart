package biz.tugay.shoppingCart.web.dto;

import biz.tugay.shoppingCart.core.entity.OrderItem;

public class OrderItemDto
{
  public String productName;

  public int itemCount;

  @SuppressWarnings("unused")
  public OrderItemDto() {
  }

  public OrderItemDto(OrderItem orderItem) {
    this.productName = orderItem.getProduct().getName();
    this.itemCount = orderItem.getItemCount();
  }
}
