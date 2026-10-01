package com.demobank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

/*
A class to add a header for correlation ID to a ServerHttpResponse being sent back to a client after
returning from a routed service after processing the request. Written in a different style than RequestTraceFilter.
Instead of implementing GlobalFilter interface, it has a @Bean factory method that returns a GlobalFilter, which
is built using Lambda expression (a compact way to write a small, nameless method inline).
Since GlobalFilter is a functional interface  (interface with only one abstract method that needs implementing
,Java 8 feature), Java lets you skip writing a whole class and just modify the method's body
as a Lambda expression.
'return (exchange, chain)' is the same as 'return new GlobalFilter() { public Mono<Void> filter(..) }'.
Java infers the 'exchange' and 'chain' types automatically from the interface, so writing full type not needed.
chain.filter(exchange) says send the request onward to whatever service the API Gateway was routing it to
.then(...) in Spring's reactive stlye, means do this next once the thing before finishes. Essentially
here it means once the entire roundtrip is finished from received Client request to Gateway, routing to service,
and received service response, run this follow-up code.
The full story, start to finish
A request hits the Gateway
RequestTraceFilter runs first (@Order(1)) — checks for an existing correlation ID, generates one if missing, attaches it as a request header
Gateway routes the request onward to whichever service (Accounts/Cards/Loans) — that header travels along with it
That downstream service does its work and responds
ResponseTraceFilter's lambda runs after that response comes back — it reads the correlation ID (still sitting on
the original request object) and copies it onto the outgoing response headers too
The final response, now carrying the correlation ID, goes back to the original caller
 */
@Configuration
public class ResponseTraceFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    String correlationId = filterUtility.getCorrelationId(requestHeaders);
                    LOGGER.debug("Updated the correlation Id to the outbound headers: {}", correlationId);
                    exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
            }));
        };
    }
}
