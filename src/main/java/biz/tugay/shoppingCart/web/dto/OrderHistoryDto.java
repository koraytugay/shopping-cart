package biz.tugay.shoppingCart.web.dto;

public class OrderHistoryDto
{
  public String productName;

  public int itemCount;

  @SuppressWarnings("unused")
  public OrderHistoryDto() {
  }

  public OrderHistoryDto(String productName, int itemCount) {
    this.productName = productName;
    this.itemCount = itemCount;
  }
}
