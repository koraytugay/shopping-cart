package biz.tugay.shoppingCart.web.filter;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.tugay.shoppingCart.web.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Gives the client a long living cookie that holds a randomly generated id to identify their shopping cart if they
 * do not have one already.
 *
 * Stored the cart id in thread context to be used in request - response scope in web layer.
 */
@Component
public class ShoppingCartIdCookieFilter
    implements Filter
{
  private final String SHOPPING_CART_ID_COOKIE_KEY = "shopping-cart-id";

  private final Predicate<Cookie> shoppingCartIdPredicate = c -> c.getName().equals(SHOPPING_CART_ID_COOKIE_KEY);

  @Override
  public void init(FilterConfig filterConfig) {
    // no op
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException
  {
    Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();

    String shoppingCartId;

    if (cookies != null && Stream.of(cookies).anyMatch(shoppingCartIdPredicate)) {
      shoppingCartId = Stream.of(cookies).filter(shoppingCartIdPredicate).findAny().map(Cookie::getValue).get();
    }
    else {
      // Give user a token they can use to identify themselves in consequent requests
      shoppingCartId = UUID.randomUUID().toString();

      Cookie shoppingCartIdCookie = new Cookie(SHOPPING_CART_ID_COOKIE_KEY, shoppingCartId);
      shoppingCartIdCookie.setMaxAge(86400 * 30);  // 30 days

      ((HttpServletResponse) servletResponse).addCookie(shoppingCartIdCookie);
    }

    RequestContext.setShoppingCartId(shoppingCartId);
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    // no op
  }
}
