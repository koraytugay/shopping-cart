package biz.tugay.shoppingCart.core.entity.compositeKey;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderIdProductSku
    implements Serializable
{
  @Basic
  @Column(name = "order_id")
  private String orderId;

  @Basic
  @Column(name = "sku")
  private String sku;

  public OrderIdProductSku() {
  }

  public OrderIdProductSku(String orderId, String sku) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderIdProductSku that = (OrderIdProductSku) o;
    return Objects.equals(orderId, that.orderId) &&
        Objects.equals(sku, that.sku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, sku);
  }
}
