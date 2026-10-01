package com.demobank.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "demobank-correlation-id";

    /*
    Check if the request has a correlation ID header and if so, what the value is. HTTP Headers can technically
    have multiple values for the same header name, so HttpHeaders.get() returns a List<String>, even though in
    practice it will usually be only one value.
    List<String> requestHeadersList.stream() turns the list into a 'stream' (sequence that can be processed).
    findFirst() grabs the first element wrapped in an Optional (container that may or may not have a value)
    get() unwraps it to get the actual String value, which is returned.
     */
    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID)!=null) {
            List<String> requestHeadersList = requestHeaders.get(CORRELATION_ID);
            return requestHeadersList.stream().findFirst().get();
        }
        else
            return null;
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

    /*
    ServerWebExchange represents info about one request/response cycle, such as request, response, metadata, etc.
    In Spring's reactive world (which Gateway uses), this object is immutable (unchangeable once created) (for thread
    safety). So a header cannot be added directly. Instead, a new copy has to be built with one change from the
    old one, new header in this case.
    exchange.getRequest() get the current request of ServerHttpRequest type
    .mutate() return a builder/preloaded copy with all current request data that can be tweaked (does not mutate in place)
    .header(name,value) tell the builder to add this one header
    .build() construct the new modified ServerHttpRequest.
    exchange.mutate() same thing but for the ServerWebExchange instead of the ServerHttpRequest. Create a builder
    .request(...) tell the ServerWebExchange builder to use the new request with added header
    .build() construct the new ServerWebExchange object which has a modified request.
    Essentially making a new copy of existing ServerWebExchange but with a modified ServerHttpRequest which has an
    added header.
     */
    private ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }
}
