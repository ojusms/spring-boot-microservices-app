package com.DemoBank.Cards;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API docs",
				description = "REST API documentation for Cards microservice of Demobank application",
				version = "v1",
				contact = @Contact(
						name = "Demobank Support",
						email = "support@demobank.com",
						url = "demobank.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "demobank.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Demobank REST API documentation",
				url = "demobank.com"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
