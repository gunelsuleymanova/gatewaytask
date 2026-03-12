package com.gateway2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class gatewayConfig {



        @Value("${services.file}")  //yamlda yazdigim file ni alir
        private String file;

        @Value("${services.blog}")  //yamlda yazilan blogu alir
        private String blog;


        @Bean
        public RouteLocator routeLocator(RouteLocatorBuilder builder) {
            return
                    builder.routes()
                            .route("file",   //routekey istediyimiz ad vere bilerik
                                    route-> route
                                            .path("/file/**") //bu file ile baslayanlari asagida yazdigim uri(file) ye gonderecek
                                            .filters(f -> f.stripPrefix(1))   //filtr edirikki file ile baslayanlarda fileni silirik
                                            .uri(file))  //requestin gedeceyi url

                            .route("demoroute",
                                    route-> route
                                            .path("/blog/**")
                                            .filters(f -> f.stripPrefix(1))
                                            .uri(blog)
                            ).build();
        }




}
