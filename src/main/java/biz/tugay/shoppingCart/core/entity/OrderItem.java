package biz.tugay.shoppingCart.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import biz.tugay.shoppingCart.core.entity.compositeKey.OrderItemId;

/**
 * An OrderItem is an entry in an Order, in other words: an Order has one to many OrderItems.
 * An `orederId` is a unique id that represents a single Order.
 * An OrderItem is identified by composite key: order-id and sku.
 */
@Entity
@Table(name = "order_item")
public class OrderItem
    implements Serializable
{
  @EmbeddedId
  private OrderItemId orderItemId;

  @Basic
  @Column(name = "item_count")
  private int itemCount;

  public OrderItem() {
  }

  public OrderItem(OrderItemId orderItemId, int itemCount) {
    this.orderItemId = orderItemId;
    this.itemCount = itemCount;
  }

  public OrderItemId getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(OrderItemId orderIdProductSku) {
    this.orderItemId = orderIdProductSku;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }
}
