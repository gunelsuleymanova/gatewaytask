package com.gateway2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class gatewayConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Value("${services.user}")
    private String user;

    @Value("${services.blog}")
    private String blog;
    @Value("${services.product}")
    private String product;


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user",
                        route -> route
                                .path("/api/v1/user/**")
                                .uri(user))
                .route("product", route -> route
                        .path("/api/v1/product/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(product)
                )
                .route("blog",
                        route -> route
                                .path("/blog/**")
                                .filters(f -> f.filter(jwtAuthenticationFilter))
                                .uri(blog))

                .build();
    }


//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return
//                builder.routes()
//                        .route("user",   //routekey istediyimiz ad vere bilerik
//                                route-> route
//                                        .path("/user/api/v1/user/**") //bu file ile baslayanlari asagida yazdigim uri(file) ye gonderecek
//                                        .filters(f -> f.filter((GatewayFilter) jwtAuthenticationFilter))   //filtr edirikki file ile baslayanlarda fileni silirik
//                                        .uri(user))  //requestin gedeceyi url
//
//                        .route("demoroute",
//                                route-> route
//                                        .path("/blog/**")
//                                        .filters(f -> f.stripPrefix(1))
//                                        .uri(blog)
//                        ).build();
//    }
//


//        @Bean
//        public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//            return
//                    builder.routes()
//                            .route("file",   //routekey istediyimiz ad vere bilerik
//                                    route-> route
//                                            .path("/file/**") //bu file ile baslayanlari asagida yazdigim uri(file) ye gonderecek
//                                            .filters(f -> f.stripPrefix(1))   //filtr edirikki file ile baslayanlarda fileni silirik
//                                            .uri(file))  //requestin gedeceyi url
//
//                            .route("demoroute",
//                                    route-> route
//                                            .path("/blog/**")
//                                            .filters(f -> f.stripPrefix(1))
//                                            .uri(blog)
//                            ).build();
//        }


}
