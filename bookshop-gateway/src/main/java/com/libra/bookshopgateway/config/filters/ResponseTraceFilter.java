package com.libra.bookshopgateway.config.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Configuration
public class ResponseTraceFilter implements GlobalFilter {

    public static final String X_RESPONSE_ID = "X-Response-Id";
    public static final String X_REQUEST_ID = "X-Request-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getResponse().getHeaders().containsKey(X_RESPONSE_ID)) {
            return chain.filter(exchange);
        }
        String requestId = Objects.requireNonNull(exchange.getRequest().getHeaders().get(X_REQUEST_ID)).get(0);
        exchange.getResponse().getHeaders().add(X_RESPONSE_ID, requestId);
        return filter(exchange, chain);
    }
}
