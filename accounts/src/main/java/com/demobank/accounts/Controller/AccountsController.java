package com.demobank.accounts.Controller;

import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.DTO.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    // POST mapping available at "/api/create".
    // The data passed from HTTP request is bound to the method parameter of type CustomerDTO
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO) {
        return null;
    }
}