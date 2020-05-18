package biz.tugay.shoppingCart.core.entity.compositeKey;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import biz.tugay.shoppingCart.core.entity.Product;

@Embeddable
public class ShoppingCartProductId
    implements Serializable
{
  @Basic
  @Column(name = "cart_id", length = 36)
  private String cartId;

  @ManyToOne
  private Product product;

  public ShoppingCartProductId() {
  }

  public ShoppingCartProductId(String cartId, Product product) {
    this.cartId = cartId;
    this.product = product;
  }

  public String getCartId() {
    return cartId;
  }

  public void setCartId(String cartId) {
    this.cartId = cartId;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
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
    ShoppingCartProductId that = (ShoppingCartProductId) o;
    return cartId.equals(that.cartId) &&
        product.equals(that.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cartId, product);
  }
}
