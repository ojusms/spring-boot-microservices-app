package com.demobank.accounts.Controller;

import com.demobank.accounts.Constants.AccountsConstants;
import com.demobank.accounts.DTO.AccountsContactInfoDTO;
import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.DTO.ErrorResponseDTO;
import com.demobank.accounts.DTO.ResponseDTO;
import com.demobank.accounts.Service.IAccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "REST APIs for Accounts service of DemoBank", // update swagger api docs
        description = "REST API docs of CREATE, READ, UPDATE, and DELETE operations for Accounts service of DemoBank")
public class AccountsController {

    /* add AccountsService field for saving the Accounts object. Add Lombok annotation for all args
     constructor to class so Spring can do the autowiring since there is only 1 constructor
     */
    IAccountsService iAccountsService;

    // add a logger to demonstrate retry mechanism for /build-info via Resilience4J
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    /* using @Value annotation to show how external property/confguration can be injected during runtime.
    In this case the value is taken from the application.yml file. Since this field is not a Component,
    SpringBoot cannot inject it via Constructor injection. So the Lombok @AllArgsConstructor must be removed
    and a constructor for only IAccountsService field must be manually created.
     */
    @Value("${build.version}")
    private String buildVersion;

    /*
    Using a variable of Spring Core's Environment interface to show how properties/configs can be injected during
    runtime. Here external properties from the system running the app are injected in the REST method below.
    Since it is managed by Spring, it can be constructor injected.
     */
    private final Environment environment;

    /*
    Declaring a field of AccountsContactInfoDTO record to demonstrate how @ConfigurationProperties can be used
    to inject values during runtime from external source. Since it is managed by Spring, it can be constructor injected.
     */
    private final AccountsContactInfoDTO accountsContactInfoDTO;

    public AccountsController(IAccountsService iAccountsService, Environment environment, AccountsContactInfoDTO accountsContactInfoDTO) {
        this.iAccountsService = iAccountsService;
        this.environment = environment;
        this.accountsContactInfoDTO = accountsContactInfoDTO;
    }

    // POST mapping available at "/api/create".
    // The data passed from HTTP request is bound to the method parameter of type CustomerDTO
    @Operation(
            summary = "CREATE REST API", // update individual api doc in swagger ui
    description = "REST API to create a Customer and Account in DemoBank")
    @ApiResponse(description = "HTTP Status CREATED", // update response schema in swagger api doc ui
    responseCode = "201")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        // save the account using the new Service object
        iAccountsService.createAccount(customerDTO);
        // returning ResponseEntity instead of ResponseDTO directly because it allows us to
        // add some metadata such as HTTP status and header info, whereas in ResponseDTO, the response would
        // directly be only in the response body
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    // GET mapping at "/api/fetch" to find and return a customer's details by mobile number
    // mobileNumber method argument is mapped to the request query parameter in the url
    @Operation(
            summary = "READ REST API",
            description = "REST API to read a customer and account of DemoBank"
    )
    @ApiResponse(
            description = "HTTP Status OK",
            responseCode = "200"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits only")
            String mobileNumber) {
        CustomerDTO customerDTO = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    /*
    PUT mapping at "/api/update" to accept a customerDTO format in the body and update an existing customer
    and related account details.
     */
    @Operation(
            summary = "UPDATE REST API",
            description = "REST API to update a customer and/or account of DemoBank"
    )
    @ApiResponses({ // use this tag for multiple possible responses in the swagger api doc ui
            @ApiResponse(
                    description = "HTTP Status OK",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "HTTP Status INTERNAL_SERVER_ERROR",
                    responseCode = "500",
                    /* since error response dto is only sent from the
                    exception handler, we have to give the reference like this so it shows up in
                    the Swagger UI for API docs
                     */
                    content = @Content(
                            schema = @Schema(implementation=ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO) {
        boolean updated = iAccountsService.updateAccount(customerDTO);
        if (updated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    /*
    DELETE mapping at "/api/delete" to delete a customer and account by accepting
    mobile number in query parameter
    */
    @Operation(
            summary = "DELETE REST API",
            description = "Rest API to delete a customer and account of DemoBank"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "HTTP Status OK",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "HTTP Status INTERNAL_SERVER_EXCEPTION",
                    responseCode = "500"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be only 10 digits")
            String mobileNumber) {
        boolean deleted = iAccountsService.deleteAccount(mobileNumber);
        if (deleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    /*
    GET mapping at "api/build-info" to return current build version of Accounts Service. To demonstrate use of
    buildVersion field injected with property value
     */
    @Operation(
            summary = "Build Info REST API",
            description = "REST API to get build information of accounts service of DemoBank"
    )
    @ApiResponse(
            description = "HTTP Status OK",
            responseCode = "200"
    )
    /*
    Add a retry mechanism for this API endpoint. Defined the fallback method below which returns a static value.
    The fallback method has the same number(and type) of method parameters + 1, and the additional parameter is
    a Throwable. In this case it is only a throwable since the retry target method has no parameters.
    Demonstrated by adding a line to throw a NullPointerException and comment out the return statement
    and invoke http://localhost:8080/api/build-info and see the static value in response and logs in terminal
    and via gateway at http://localhost:8072/demobank/accounts/api/build-info (with or without demobank).
    One observed behavior when invoking through the gateway server's custom route config - the custom route
    for accounts has a circuitbreaker of its own. This has a default timeout of 1s. The waitDuration property
    value in accounts can make the difference between getting a fallback response for build-info with a static
    value or getting a fallback resposne from the gateway server. For ex. a value of 100, with exponential backoff
    factor of 2, would result in 100 + 200 (for 2nd and 3rd attempt) = ~300ms which is under 1s. But a default
    value of 500 would result in 500+1000 = ~1500ms which is > 1s and result in fallback of gateway CB.
     */
    @Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        // add a logger statement to show number of times method is invoked to demonstrate retry mechanism
        LOGGER.debug("getBuildInfo() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        LOGGER.debug("getBuildInfoFallback() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }

    /*
    GET mapping at "api/java-version" to return Java version of Accounts Service. To demonstrate use of
    Environment interface variable for external property injection
     */
    @Operation(
            summary = "Java Version REST API",
            description = "REST API to get build Java Version of accounts service of DemoBank"
    )
    @ApiResponse(
            description = "HTTP Status OK",
            responseCode = "200"
    )
    /*
    Implement rate limiting via Resilience4J. fallbackMethod parameter is optional. Without a fallback, the response
    is a 500 with ISE because of GlobalExceptionHandler. A fallback can be configured to return a static value
    or a proper rate limited response. GlobalExceptionHandler can also be configured to handle
    io.github.resilience4j.ratelimiter.RequestNotPermitted.class which extends RuntimeException
     */
    @RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many requests. Try again after some time");
    }

    /*
GET mapping at "api/contact-info" to return contact info of Accounts Service. To demonstrate use of
@ConfigurationProperties of Spring for external property value injection
 */
    @Operation(
            summary = "Contact Info REST API",
            description = "REST API to get contact info of accounts service of DemoBank"
    )
    @ApiResponse(
            description = "HTTP Status OK",
            responseCode = "200"
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDTO> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDTO);
    }
}