package biz.tugay.shoppingCart.core.entity.compositeKey;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderItemId
    implements Serializable
{
  @Basic
  @Column(name = "order_id")
  private String orderId;

  @Basic
  @Column(name = "sku")
  private String sku;

  public OrderItemId() {
  }

  public OrderItemId(String orderId, String sku) {
    this.orderId = orderId;
    this.sku = sku;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String cartId) {
    this.orderId = cartId;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }
}
