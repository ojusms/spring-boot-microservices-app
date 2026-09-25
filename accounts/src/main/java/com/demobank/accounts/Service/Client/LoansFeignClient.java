package com.demobank.accounts.Service.Client;

import com.demobank.accounts.DTO.LoansDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping("/api/fetch")
    ResponseEntity<LoansDTO> findLoan(@RequestParam String mobileNumber);
}
