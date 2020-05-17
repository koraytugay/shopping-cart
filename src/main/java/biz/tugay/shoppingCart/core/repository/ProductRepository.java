package biz.tugay.shoppingCart.core.repository;

import biz.tugay.shoppingCart.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String>
{
  Product findDistinctBySku(String sku);
}
