package com.demobank.accounts.Controller;

import com.demobank.accounts.Constants.AccountsConstants;
import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.DTO.ErrorResponseDTO;
import com.demobank.accounts.DTO.ResponseDTO;
import com.demobank.accounts.Service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@Tag(name = "REST APIs for Accounts service of DemoBank", // update swagger api docs
        description = "REST API docs of CREATE, READ, UPDATE, and DELETE operations for Accounts service of DemoBank")
public class AccountsController {

    /* add AccountsService field for saving the Accounts object. Add Lombok annotation for all args
     constructor to class so Spring can do the autowiring since there is only 1 constructor
     */
    IAccountsService iAccountsService;

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
}