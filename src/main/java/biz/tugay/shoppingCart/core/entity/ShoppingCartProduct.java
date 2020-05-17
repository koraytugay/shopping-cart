package biz.tugay.shoppingCart.core.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import biz.tugay.shoppingCart.core.entity.compositeKey.CartIdProductSku;

/**
 * Represents a product (identified by its sku), in a shopping cart (identified by a UUID) and number of that particular
 * product in the cart.
 */
@Entity
@Table(name = "shopping_cart")
public class ShoppingCartProduct
    implements Serializable
{
  @EmbeddedId
  private CartIdProductSku cartIdProductSku = new CartIdProductSku();

  @Basic
  @Column(name = "item_count")
  private int itemCount;

  public CartIdProductSku getCartIdProductSku() {
    return cartIdProductSku;
  }

  public void setCartIdProductSku(CartIdProductSku shoppingCartId) {
    this.cartIdProductSku = shoppingCartId;
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
    ShoppingCartProduct that = (ShoppingCartProduct) o;
    return Objects.equals(cartIdProductSku, that.cartIdProductSku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cartIdProductSku);
  }
}
