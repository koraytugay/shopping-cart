package biz.tugay.shoppingCart.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;

/**
 * Represents a product (identified by its sku) in a shopping cart (identified by a UUID) and the item count of that
 * product in that cart.
 */
@Entity
@Table(name = "shopping_cart")
public class ShoppingCartProduct
    implements Serializable
{
  @EmbeddedId
  private ShoppingCartProductId shoppingCartProductId = new ShoppingCartProductId();

  @Basic
  @Column(name = "item_count")
  private int itemCount;

  public ShoppingCartProductId getShoppingCartProductId() {
    return shoppingCartProductId;
  }

  public void setShoppingCartProductId(ShoppingCartProductId shoppingCartId) {
    this.shoppingCartProductId = shoppingCartId;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }
}
