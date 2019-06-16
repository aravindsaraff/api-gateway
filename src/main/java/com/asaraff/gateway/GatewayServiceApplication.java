
package com.asaraff.gateway;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Exclude ErrorMvcAutoConfiguration class in order to override error handling.
 */
@SpringBootApplication(/*scanBasePackages = {"com.asaraff.api_portal"},*/ exclude = ErrorMvcAutoConfiguration.class)
@EnableSwagger2
public class GatewayServiceApplication  {

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(GatewayServiceApplication.class)
      .bannerMode(Banner.Mode.OFF)
     // .listeners(new LoadAdditionalProperties())
      .run(args);
  }
}
