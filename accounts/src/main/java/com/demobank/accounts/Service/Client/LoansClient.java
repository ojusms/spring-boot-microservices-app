package com.demobank.accounts.Service.Client;

import com.demobank.accounts.DTO.LoansDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api")
public interface LoansClient {

    @GetExchange(value = "/fetch", accept = "application/json")
    ResponseEntity<LoansDTO> findLoan(@RequestHeader("demobank-correlation-id") String number,
                                      @RequestParam String mobileNumber);
}
