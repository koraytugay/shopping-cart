package biz.tugay.shoppingCart.core.entity.compositeKey;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CartIdProductSku
    implements Serializable
{
  @Basic
  @Column(name = "cart_id")
  private String cartId;

  @Basic
  @Column(name = "sku")
  private String sku;

  public CartIdProductSku() {
  }

  public CartIdProductSku(String cartId, String sku) {
    this.cartId = cartId;
    this.sku = sku;
  }

  public String getCartId() {
    return cartId;
  }

  public void setCartId(String cartId) {
    this.cartId = cartId;
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
    CartIdProductSku that = (CartIdProductSku) o;
    return Objects.equals(cartId, that.cartId) &&
        Objects.equals(sku, that.sku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cartId, sku);
  }
}
