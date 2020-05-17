package biz.tugay.shoppingCart.core.entity.compositeKey;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ShoppingCartProductId
    implements Serializable
{
  @Basic
  @Column(name = "cart_id")
  private String cartId;

  @Basic
  @Column(name = "sku")
  private String sku;

  public ShoppingCartProductId() {
  }

  public ShoppingCartProductId(String cartId, String sku) {
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
}
