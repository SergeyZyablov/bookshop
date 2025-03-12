package com.libra.bookshopgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    public static final String SEGMENT = "/${segment}";
    public static final String SERVICE_HEADER = "Service";

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/libra/book/**")
                        .filters(f -> f.rewritePath("/libra/book/(?<segment>.*)", SEGMENT)
                                .addResponseHeader(SERVICE_HEADER, "bookshop-book"))
                        .uri("lb://BOOKSHOP-BOOK"))
                .route(r -> r.path("/libra/user/**")
                        .filters(f -> f.rewritePath("/libra/user/(?<segment>.*)", SEGMENT)
                                .addResponseHeader(SERVICE_HEADER, "bookshop-user"))
                        .uri("lb://BOOKSHOP-USER"))
                .route(r -> r.path("/libra/order/**")
                        .filters(f -> f.rewritePath("/libra/order/(?<segment>.*)", SEGMENT)
                                .addResponseHeader(SERVICE_HEADER, "bookshop-order"))
                        .uri("lb://BOOKSHOP-ORDER"))
                .route(r -> r.path("/libra/test/**")
                        .filters(f -> f.rewritePath("/libra/test/(?<segment>.*)", SEGMENT)
                                .addResponseHeader(SERVICE_HEADER, "bookshop-test")
                                .circuitBreaker(config -> config
                                        .setName("testMSCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                        )
                        .uri("lb://BOOKSHOP-TEST"))
                .build();
    }
}
