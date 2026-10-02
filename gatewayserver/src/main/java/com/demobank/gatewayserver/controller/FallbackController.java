package com.demobank.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/*
Adding a controller package with a FallbackController class to implement fallback mechanism for the
Gateway circuit breaker.
 */

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<String> fallbackMethod() {
        return Mono.just("An error occurred. Please try again later or contact customer support.");
    }
}
