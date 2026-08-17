package com.demobank.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // tell spring to enable auditing with the bean to use.
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Docs",
				description = "REST API documentation for Accounts microservice of Demobank application",
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
				description = "Demobank REST APIs documentation",
				url = "demobank.com/swagger-ui.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
