package com.demobank.accounts.Service.Client;

import com.demobank.accounts.DTO.CardsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/*
Annotate the interface as a FeignClient for component scanning and autowiring.
The 'value'/'name' parameter value has to be the logical name of the service registered in Eureka, being invoked.
Spring.application.name of that service. "Cards" in this case. Eureka Server is case-insensitive.
This name gets resolved to the 'host:port' part in
Spring Cloud LoadBalancer querying Eureka's registry. Feign talks to SB LoadBalancer which talks to Eureka.
Changing Eureka Server to any other Service Registry like Consul or Kubernetes does not require any modification to
FeignClient interface.
The abstract method uses declarative naming syntax (similar to JpaRepository for the abstract methods).
Here the HTTP method and path must be same as the service REST method to be invoked (Cards service's GET /api/fetch
in this case). The abstract method name can be different from the controller method name.
The return type and method signature need to be compatible
enough to serialize into what target service expects and deserialize what target returns.
Updated to enable & implement Spring Cloud CircuitBreaker fallbacks by setting the 'fallback' attribute of
@FeignClient with the fallback class.
 */
@FeignClient(value = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping("/api/fetch")
    ResponseEntity<CardsDTO> findCard(@RequestHeader("demobank-correlation-id") String number,
                                      @RequestParam String mobileNumber);
}

/*
A fallback class for the OpenFeign circuit breaker. This is the default code path that is executed when there
is an error or the circuit is open. The class needs to be declared as a Spring Bean.
 */
@Component
class CardsFallback implements  CardsFeignClient {

    @Override
    public ResponseEntity<CardsDTO> findCard(String number, String mobileNumber) {
        return null;
    }
}
