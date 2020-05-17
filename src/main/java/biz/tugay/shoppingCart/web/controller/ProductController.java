package biz.tugay.shoppingCart.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController
{
  private ProductRepository productRepository;

  @Autowired
  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping
  public List<ProductDto> getAllProducts() {
    return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
  }
}
