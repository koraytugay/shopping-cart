package biz.tugay.shoppingCart.web.dto;

public class OrderItemDto
{
  public String productName;

  public int itemCount;

  @SuppressWarnings("unused")
  public OrderItemDto() {
  }

  public OrderItemDto(String productName, int itemCount) {
    this.productName = productName;
    this.itemCount = itemCount;
  }
}
