package com.demobank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
