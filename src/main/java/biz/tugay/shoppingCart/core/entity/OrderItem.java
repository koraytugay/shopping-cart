package biz.tugay.shoppingCart.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import biz.tugay.shoppingCart.core.entity.compositeKey.OrderItemId;

/**
 * An OrderItem is an entry in an Order, in other words: an Order has one to many OrderItems.
 * An `orderId` is a unique id that represents a single Order.
 * An OrderItem is identified by composite key: order-id and a unique Product.
 */
@Entity
@Table(name = "order_item")
public class OrderItem
    implements Serializable
{
  @EmbeddedId
  private OrderItemId id;

  @Basic
  @Column(name = "item_count")
  private int itemCount;

  public OrderItem() {
  }

  public OrderItem(OrderItemId id, int itemCount) {
    this.id = id;
    this.itemCount = itemCount;
  }

  public OrderItemId getId() {
    return id;
  }

  public void setId(OrderItemId orderIdProductSku) {
    this.id = orderIdProductSku;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  /**
   * Convenience method to get product from composite key.
   */
  @Transient
  public Product getProduct() {
    return id.getProduct();
  }
}
