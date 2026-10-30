package com.demobank.accounts.Service.Client;

import com.demobank.accounts.DTO.CardsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api")
public interface CardsClient {

    @GetExchange(value = "/fetch", accept = "application/json")
    ResponseEntity<CardsDTO> fetch(@RequestParam String mobileNumber);
}
