package biz.tugay.shoppingCart;

import biz.tugay.shoppingCart.core.entity.Product;
import biz.tugay.shoppingCart.core.entity.ShoppingCartProduct;
import biz.tugay.shoppingCart.core.entity.compositeKey.ShoppingCartProductId;
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

  public Product newPersistedProduct(String sku, String name, String description) {
    Product product = new Product(sku);
    product.setName(name);
    product.setDescription(description);
    productRepository.save(product);
    return product;
  }

  public ShoppingCartProduct newPersistedShoppingCartProduct(String shoppingCartId, Product product, int count) {
    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
    shoppingCartProduct.setShoppingCartProductId(new ShoppingCartProductId(shoppingCartId, product));
    shoppingCartProduct.setItemCount(count);

    shoppingCartProductRepository.save(shoppingCartProduct);

    return shoppingCartProduct;
  }
}
