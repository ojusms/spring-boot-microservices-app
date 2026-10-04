package com.demobank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

/*
An edge server/gateway server/API gateway is a server which acts as a single entry point for external traffic
into the microservices network. External clients are not granted access to directly invoke the service instances
but can reach them via the API gateway. The API gateway also has capabilities to perform cross-cutting concerns
like logging, monitoring, traces, etc. and even perform AuthN & AuthZ. In Spring, it is a project of
Spring Cloud Gateway with necessary dependencies in the classpath.
 */
@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	/*
	Using Fluent Java Routes API to create simple custom route configuration using RouteLocatorBuilder.
	The API calls to API Gateway were made at localhost:8072/service-name/api-path. This custom config
	enables the path localhost:8072/demobank/service-name/api-path. Both default and custom routes exist
	simultaneously currently. To disable default routes, set
	spring.cloud.gateway.server.webflux.discovery.locator property to false.
	Updates routes visible at http://localhost:8072/actuator/gateway/routes.
	 */
    /*
    Added a circuit breaker inside the custom route config method below by adding '.circuitbreaker()' to
    the filters() method of route() for accounts service. This is an uncommon way of implementing a
    circuit breaker. The most common way is on a method level directly by using @CircuitBreaker annotation.
    One interesting documented observation on the behavior of circuit breaker is that it considers
    timeouts as 'failedCalls' (which can be seen in the actuator endpoint http://localhost:8072/actuator/circuitbreakers)
    and not calls with error respone of 500. A likely explanation is that Spring Cloud Gateway's Circuit Breaker
    uses Spring Reactive, which wraps the downstream call as a reactive 'Mono' to error-out, which is a true
    faulire signal the circuit breaker can observe and count. When a service call thrown an uncaught exception
    resulting in 500 internally, Spring's default exception handling catches that and converts it into a
    normal, successfully completed HTTP response - just one with a 500 code sitting in the headers.
    From Gateway's reactive point-of-view, the call still completed successfully - it just happens to carry
    an unwanted status code. The Gateway Circuit Breaker does not see it as a failure signal, because none
    occurred at the reactive-stream level. The fix is to explicitly tell the circuit breaker which status
    code to count as a failure.This can be done with properties or in the below case, by using
    '.setStatusCode()' along with '.setName()' on config expression lambda inside 'circuitBreaker()'.
    In order to demonstrate circuit breaker state transition from 'closed' to 'open' and to 'half-open' via
    timeout based failure, the Accounts API of /build-info was modified with a Thread.sleep(5000). By default,
    circuit breaker waits for 1s before considering as a timeout and failing.
     */

	@Bean
	public RouteLocator demobankRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/demobank/accounts/**")
						.filters(f -> f.rewritePath("/demobank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()) //add custom field to response header for this custom route
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker") // add a circuit breaker for accounts service with custom name
                                        .setFallbackUri("forward:/fallback"))) // add a fallback in case of failure. This method returns a 200 with the message defined.
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/demobank/cards/**")
						.filters(f -> f.rewritePath("/demobank/cards/(?<segment>.*)","/${segment}")
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET) // add retry with backoff for this route
										.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/demobank/loans/**")
						.filters(f -> f.rewritePath("/demobank/loans/(?<segment>.*)","/${segment}"))
						.uri("lb://LOANS")).build();
	}


}
