package biz.tugay.shoppingCart.core.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product
    implements Serializable
{
  @Id
  @Column(name = "sku", length = 36)
  private String sku;

  @Basic
  @Column(name = "name")
  private String name;

  @Basic
  @Column(name = "description")
  private String description;

  public Product() {
  }

  public Product(String sku) {
    this.sku = sku;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return sku.equals(product.sku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku);
  }
}
