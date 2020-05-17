package biz.tugay.shoppingCart.core.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import biz.tugay.shoppingCart.core.entity.compositeKey.OrderIdProductSku;

/**
 * Represents an order item. A complete order is one or more OrderItem entities.
 */
@Entity
@Table(name = "order_item")
public class OrderItem
    implements Serializable
{
  @EmbeddedId
  private OrderIdProductSku orderIdProductSku;

  @Basic
  @Column(name = "item_count")
  private int itemCount;

  public OrderItem() {
  }

  public OrderItem(OrderIdProductSku orderIdProductSku, int itemCount) {
    this.orderIdProductSku = orderIdProductSku;
    this.itemCount = itemCount;
  }

  public OrderIdProductSku getOrderIdProductSku() {
    return orderIdProductSku;
  }

  public void setOrderIdProductSku(OrderIdProductSku orderIdProductSku) {
    this.orderIdProductSku = orderIdProductSku;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
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
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(orderIdProductSku, orderItem.orderIdProductSku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderIdProductSku);
  }
}
