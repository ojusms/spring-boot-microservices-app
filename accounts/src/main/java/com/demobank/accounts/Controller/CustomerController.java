package com.demobank.accounts.Controller;

import com.demobank.accounts.DTO.CustomerDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "REST APIs for Customer details of Accounts service of DemoBank", // update swagger api docs
        description = "REST API docs to fetch Customer details of DemoBank")
public class CustomerController {

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
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}" , message = "Mobile number must be 10 digits")
            String mobileNumber) {
        return null;
    }
}
