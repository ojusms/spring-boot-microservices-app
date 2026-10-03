package com.demobank.accounts.Service.Client;

import com.demobank.accounts.DTO.LoansDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping("/api/fetch")
    ResponseEntity<LoansDTO> findLoan(@RequestHeader("demobank-correlation-id") String number,
                                      @RequestParam String mobileNumber);
}

@Component
class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDTO> findLoan(String number, String mobileNumber) {
        return null;
    }
}