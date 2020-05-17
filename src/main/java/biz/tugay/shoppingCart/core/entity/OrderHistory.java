package biz.tugay.shoppingCart.core.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import biz.tugay.shoppingCart.core.entity.compositeKey.OrderIdProductSku;

@Entity
@Table(name = "order_history")
public class OrderHistory
    implements Serializable
{
  @EmbeddedId
  private OrderIdProductSku orderIdProductSku;

  @Basic
  @Column(name = "item_count")
  private long itemCount;

  public OrderHistory() {
  }

  public OrderHistory(OrderIdProductSku orderIdProductSku, long itemCount) {
    this.orderIdProductSku = orderIdProductSku;
    this.itemCount = itemCount;
  }

  public OrderIdProductSku getOrderIdProductSku() {
    return orderIdProductSku;
  }

  public void setOrderIdProductSku(OrderIdProductSku orderIdProductSku) {
    this.orderIdProductSku = orderIdProductSku;
  }

  public long getItemCount() {
    return itemCount;
  }

  public void setItemCount(long itemCount) {
    this.itemCount = itemCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderHistory order = (OrderHistory) o;
    return Objects.equals(orderIdProductSku, order.orderIdProductSku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderIdProductSku);
  }
}
