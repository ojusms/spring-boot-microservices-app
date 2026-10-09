package com.demobank.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

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
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET) // add retry with backoff for this route
										.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/demobank/loans/**")
						.filters(f -> f.rewritePath("/demobank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(keyResolver())))
						.uri("lb://LOANS")).build();
	}

    /*
    A method to change the Gateway CircuitBreaker timeout from default 1s to 4s.
    ReactiveResilience4JCircuitBreakerFactory is the factory Spring Cloud uses to create
    your circuit breakers (like accountsCircuitBreaker). "Reactive" is there because the Gateway
    is WebFlux-based. Customizer<...> is a small interface with one method that receives the factory
    so you can adjust it. Spring finds this bean at startup and applies it.
    Customizer has a single method, so a lambda can implement it. factory -> means "here's the factory,
    do this to it." It's an expression lambda with no braces, because the body is one method call.
    configureDefault(...) says "when you create a circuit breaker, use this recipe unless it has its own
    specific config."
    id -> ... is a second, nested lambda. Spring calls it with the circuit breaker's name
    (id, e.g. "accountsCircuitBreaker"), and it must return the config to use for that breaker.
    new Resilience4JConfigBuilder(id)... - This is a builder, with the same chain-then-.build() pattern
    as routes.
    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()) sets the circuit breaker's rules (sliding window,
    failure threshold, and so on) to Resilience4j's built-in defaults.
    .timeLimiterConfig(...) sets the timeout rules. TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4))
    .build() means "start from defaults, but make the timeout 4 seconds.". The final .build() produces the
    finished configuration object that the outer lambda returns.
     */
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id ->
				new Resilience4JConfigBuilder(id)
						.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
						.timeLimiterConfig(TimeLimiterConfig.custom()
								.timeoutDuration(Duration.ofSeconds(4))
								.build())
						.build());
	}

	/*
	Rate Limiter pattern is a design pattern for resiliency, like Circuit Breaker and Retry, which improves
	a systems robustness and availability by limiting the access to resources for users based on different criteria.
	The Redis implementation of RateLimiter in Spring Cloud Gateway uses the TokenBucket algorithm for
	Request rate limiting (limiting number of requests for an API per second/unit of time for each client).
	ReplenishRate is the number of tokens added to the bucket per second.
	BurstCapacity is the total number of tokens the bucket can hold at a time.
	RequestedTokens is the number of tokens a request costs. All 3 all excluding dropped requests.
	values of (1,1,1) means 1 request per second for that API. (10,10,2) means 5 requests/second for that API.
	When client exceeds the rate limit, gateway server responds with HTTP 429 - Too Many Requests (default status).
	This requires Redis datastore, can be run as a container with 'docker run -d --name redis-ms -p 6379:6379 redis'
	and properties can be configured to interact with it. Buckets live in Redis and not Gateway's memory, that way
	if several Gateway instances are run, they all share one count per client.
	This can be tested locally with Apache Benchmark
	 */
	/*
	Creating a bean of RedisRateLimiter to use in the custom route config GatewayFilterSpec above for Loans
	 */
	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1,1,1);
	}

	/*
	Creating a bean of KeyResolver interface to use in custom route config for Loans above.
	A rate limiter needs to know who is making the request so each caller can get their own bucket.
	a KeyResolver answers this by returning a string key per request.
	KeyResolver is a functional interface with one method Mono<String> resolve(ServerWebExchange exchange),
	so a lambda can implement it. getHeaders().getFirst("user") reads the value of a request header named
	user, or null if it's absent.
	Mono.justOrEmpty(x) wraps x in a Mono, which is a container for zero or one value delivered reactively.
	If x is null, you get an empty Mono instead of an error.
	.defaultIfEmpty("anonymous") means that if the Mono ended up empty, use "anonymous" as the key.
	Some caveats with this resolver -
	- All requests without a user header share one bucket called anonymous. Several anonymous callers would be
	rate-limited together.
	- The header is client-controlled. Anyone can send a different user value on each request and get a fresh
	bucket every time. It's fine for a demo, but real systems derive the key from something trustworthy,
	such as the authenticated user or the client IP.
	The defaultIfEmpty also matters because, by default, Gateway rejects requests whose key resolves to empty
	(with a 403), so the fallback value keeps header-less calls from being blocked outright.
	 */
	@Bean
	public KeyResolver keyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}

}
