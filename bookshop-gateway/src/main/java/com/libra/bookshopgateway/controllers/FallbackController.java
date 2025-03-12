package com.libra.bookshopgateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("Service unavailable, please try again later");
    }
}
