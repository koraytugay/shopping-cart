package biz.tugay.shoppingCart.web;

public class RequestContext
{
  private static ThreadLocal<String> shoppingCartId = new ThreadLocal<>();

  public static String getShoppingCartId() {
    return shoppingCartId.get();
  }

  public static void setShoppingCartId(String shoppingCartId) {
    RequestContext.shoppingCartId.set(shoppingCartId);
  }
}
