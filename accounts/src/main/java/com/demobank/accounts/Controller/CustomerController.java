package com.demobank.accounts.Controller;

import com.demobank.accounts.DTO.CustomerDetailsDTO;
import com.demobank.accounts.Service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "REST APIs for Customer details of Accounts service of DemoBank", // update swagger api docs
        description = "REST API docs to fetch Customer details of DemoBank")
@AllArgsConstructor
public class CustomerController {

    private final ICustomersService iCustomersService;
    // add a logger to log correlation ID in request header from API Gateway
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    /* GET mapping at "/api/fetchCustomerDetails" to find and return a customer's details by mobile number
     mobileNumber method argument is mapped to the request query parameter in the url
     */
    @Operation(
            summary = "READ REST API",
            description = "REST API to read a customer's accounts, loans, and cards details of DemoBank"
    )
    @ApiResponse(
            description = "HTTP Status OK",
            responseCode = "200"
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(
            // modify method signature to accept a parameter annotated for request header of correlation ID
            @RequestHeader("demobank-correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}" , message = "Mobile number must be 10 digits")
            String mobileNumber) {
        // log the correlation ID in the request header from API Gateway at a debug level
        LOGGER.debug("demobank-correlation-id found: {}", correlationId);
        CustomerDetailsDTO customerDetailsDTO = iCustomersService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDTO);
    }
}
