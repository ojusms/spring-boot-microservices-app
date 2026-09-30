package com.demobank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
	@Bean
	public RouteLocator demobankRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/demobank/accounts/**")
						.filters(f -> f.rewritePath("/demobank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))	//add custom field to response header for this custom route
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/demobank/cards/**")
						.filters(f -> f.rewritePath("/demobank/cards/(?<segment>.*)","/${segment}"))
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/demobank/loans/**")
						.filters(f -> f.rewritePath("/demobank/loans/(?<segment>.*)","/${segment}"))
						.uri("lb://LOANS")).build();
	}
	 */

}
