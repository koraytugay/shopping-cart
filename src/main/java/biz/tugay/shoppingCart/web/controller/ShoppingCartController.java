package biz.tugay.shoppingCart.web.controller;

import java.util.ArrayList;
import java.util.List;

import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
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
@RequestMapping(value = ShoppingCartController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController
{
  public static final String PATH = "/api/shoppingCart";

  private ShoppingCartService shoppingCartService;

  @Autowired
  public ShoppingCartController(ShoppingCartService shoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  @PostMapping("{sku}/{itemCount}")
  public void addOrRemoveProduct(@PathVariable("sku") String sku, @PathVariable("itemCount") int itemCount) {
    shoppingCartService.updateCartUpdateProductByItemCount(RequestContext.getShoppingCartId(), sku, itemCount);
  }

  @GetMapping
  public List<ShoppingCartProductDto> getShoppingCart() {
    List<ShoppingCartProduct> shoppingCartContents
        = shoppingCartService.getShoppingCartContents(RequestContext.getShoppingCartId());

    List<ShoppingCartProductDto> shoppingCartProductDtos = new ArrayList<>();
    for (ShoppingCartProduct shoppingCartProduct : shoppingCartContents) {
      ProductDto productDto = new ProductDto(shoppingCartProduct.getProduct());
      shoppingCartProductDtos.add(new ShoppingCartProductDto(productDto, shoppingCartProduct.getItemCount()));
    }

    return shoppingCartProductDtos;
  }
}
