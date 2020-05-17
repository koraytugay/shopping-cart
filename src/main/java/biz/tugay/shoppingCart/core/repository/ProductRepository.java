package biz.tugay.shoppingCart.core.repository;

import biz.tugay.shoppingCart.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>
{
  Product findDistinctBySku(String sku);
}
