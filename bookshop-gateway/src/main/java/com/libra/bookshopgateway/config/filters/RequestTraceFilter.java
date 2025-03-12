package com.libra.bookshopgateway.config.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.libra.bookshopgateway.config.filters.FilterUtility.generateRequestId;

@Order(1)
@Component
@Slf4j
public class RequestTraceFilter implements GlobalFilter {

    public static final String REQUEST_ID = "X-Request-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();

        if (headers.get(REQUEST_ID) != null) {
            log.debug("X-Request-Id: {}", headers.get(REQUEST_ID));
        } else {
            String requestId = generateRequestId();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(builder -> builder.header(REQUEST_ID, requestId))
                    .build();

            return chain.filter(mutatedExchange);
        }
        return chain.filter(exchange);
    }
}
