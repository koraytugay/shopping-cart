package biz.tugay.shoppingCart.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.service.ShoppingCartService;
import biz.tugay.shoppingCart.web.RequestContext;
import biz.tugay.shoppingCart.web.dto.ProductDto;
import biz.tugay.shoppingCart.web.dto.ShoppingCartProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/shoppingCart", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController
{
  private ShoppingCartService shoppingCartService;

  @Autowired
  public ShoppingCartController(ShoppingCartService shoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  @PostMapping("{sku}/{itemCount}")
  public void addOrRemoveProduct(@PathVariable("sku") String sku, @PathVariable("itemCount") long itemCount) {
    shoppingCartService.updateCartUpdateProductByItemCount(RequestContext.getShoppingCartId(), sku, itemCount);
  }

  @GetMapping
  public List<ShoppingCartProductDto> getShoppingCart() {
    List<ShoppingCartProductDto> shoppingCartProductList = new ArrayList<>();

    String shoppingCartId = RequestContext.getShoppingCartId();
    Map<Product, Long> shoppingCartContents = shoppingCartService.getShoppingCartContents(shoppingCartId);

    shoppingCartContents.forEach((product, itemCount) -> {
      ProductDto productResponseDto = new ProductDto(product);
      ShoppingCartProductDto shoppingCartProductDto = new ShoppingCartProductDto(productResponseDto, itemCount);

      shoppingCartProductList.add(shoppingCartProductDto);
    });

    return shoppingCartProductList;
  }
}
