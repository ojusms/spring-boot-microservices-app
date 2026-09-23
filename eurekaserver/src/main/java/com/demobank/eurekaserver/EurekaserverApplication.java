package com.demobank.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*
Eureka Server, part of Spring Cloud Netflix, is a Service Registry server that keeps track of microservices
instances such as their IP, DNS, metadata, etc. Does Service Discovery by giving relevant info to microservice
instances when they want to know how to contact other microservice instances for communication
(like backing services or downstream dependencies) in client-side-service-discovery.
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaserverApplication.class, args);
	}

}
