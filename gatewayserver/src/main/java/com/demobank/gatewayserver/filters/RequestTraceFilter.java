package com.demobank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
A class to intercept requests from a client and add a trace/correlation ID in the header.
GlobalFilter is a functional interface that runs on all requests passing through the Gateway, regardless
of where it is being routed to. If there are multiple GlobalFilter implementations, @Order(1) ensures
this one is run first
 */

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    /*
    This is a single method present in GlobalFilter interface that has to be overridden.
    Mono<Void> is part of Spring's reactive programming (way to write non-blocking code), and it Void
    means it does not return a value but rather signals 'done'. Essentially the method gets the
    ServerWebExchange's ServerHttpRequest and checks if a header for correlation ID is present or not, and
    add one if not. If found or added, log the same at DEBUG level. The correlation ID generated is a
    unique random UUID.
    return chain.filter(exchange) basically says 'let the request continue on its journey', passing along
    the modified or unmodified ServerWebExchange object with correlation ID header in the request to
    travel to wherever the API Gateway routes it to. This filter runs on the way in, when a client request
    reaches th API Gateway before being routed to other services.
     */
    /**
     * Process the Web request and (optionally) delegate to the next {@code GatewayFilter}
     * through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            LOGGER.debug("demobank-correlation-id found in RequestTraceFilter: {}",
                    filterUtility.getCorrelationId(requestHeaders));
        }
        else {
            String correlationId = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationId);
            LOGGER.debug("demobank-correlation-id generated in RequestTraceFilter: {}", correlationId);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (filterUtility.getCorrelationId(requestHeaders)!=null){
            return true;
        } else {
            return false;
        }
    }

    private String generateCorrelationId() {
    return java.util.UUID.randomUUID().toString();
    }
}
