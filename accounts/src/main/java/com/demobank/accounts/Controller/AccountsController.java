package com.demobank.accounts.Controller;

import com.demobank.accounts.Constants.AccountsConstants;
import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.DTO.ResponseDTO;
import com.demobank.accounts.Service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    /* add AccountsService field for saving the Accounts object. Add Lombok annotation for all args
     constructor to class so Spring can do the autowiring since there is only 1 constructor
     */
    IAccountsService iAccountsService;

    // POST mapping available at "/api/create".
    // The data passed from HTTP request is bound to the method parameter of type CustomerDTO
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO) {
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
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDTO customerDTO = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }
}