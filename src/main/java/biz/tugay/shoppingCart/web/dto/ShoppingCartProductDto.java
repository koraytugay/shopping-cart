package biz.tugay.shoppingCart.web.dto;

/**
 * Represents a product in a shopping cart. Encapsulates a product and the count of the product in the cart.
 * <pre>
 * {
 *   "product": {
 *     "sku": "1234-abcd"...,
 *     "name": "pizza",
 *     "description": "delicious"
 *   },
 *   "count": 15
 * }
 * </pre>
 */
public class ShoppingCartProductDto
{
  public ProductDto product;

  public int count;

  @SuppressWarnings("unused")
  public ShoppingCartProductDto() {
  }

  public ShoppingCartProductDto(ProductDto product, int count) {
    this.product = product;
    this.count = count;
  }
}
