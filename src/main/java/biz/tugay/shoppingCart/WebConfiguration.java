package biz.tugay.shoppingCart;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// TODO: There is probably a better way to do this.. This is required for Spring backend to forward anything to
// index.html so that React can proxy it. Stolen from: https://stackoverflow.com/questions/39331929
@Configuration
public class WebConfiguration
    extends WebMvcConfigurerAdapter
{
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/{spring:\\w+}").setViewName("forward:/");
    registry.addViewController("/**/{spring:\\w+}").setViewName("forward:/");
    registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");
  }
}
