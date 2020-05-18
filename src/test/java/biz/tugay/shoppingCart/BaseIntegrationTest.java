package biz.tugay.shoppingCart;

import biz.tugay.shoppingCart.core.repository.OrderItemRepository;
import biz.tugay.shoppingCart.core.repository.ProductRepository;
import biz.tugay.shoppingCart.core.repository.ShoppingCartProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest
{
  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ShoppingCartProductRepository shoppingCartProductRepository;

  @Before
  @After
  public void refreshDatabase() {
    orderItemRepository.deleteAll();
    shoppingCartProductRepository.deleteAll();
    productRepository.deleteAll();
  }
}
