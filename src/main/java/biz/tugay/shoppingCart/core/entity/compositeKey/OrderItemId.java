package biz.tugay.shoppingCart.core.entity.compositeKey;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import biz.tugay.shoppingCart.core.entity.Product;

@Embeddable
public class OrderItemId
    implements Serializable
{
  @Basic
  @Column(name = "order_id", length = 36)
  private String orderId;

  @ManyToOne
  private Product product;

  public OrderItemId() {
  }

  public OrderItemId(String orderId, Product product) {
    this.orderId = orderId;
    this.product = product;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String cartId) {
    this.orderId = cartId;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(final Product product) {
    this.product = product;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderItemId that = (OrderItemId) o;
    return orderId.equals(that.orderId) &&
        product.equals(that.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, product);
  }
}
