package biz.tugay.shoppingCart.web.dto;

import biz.tugay.shoppingCart.core.entity.Product;

public class ProductDto
{
  public String sku;

  public String name;

  public String description;

  @SuppressWarnings("unused")
  public ProductDto() {
  }

  public ProductDto(Product product) {
    this.sku = product.getSku();
    this.name = product.getName();
    this.description = product.getDescription();
  }
}
