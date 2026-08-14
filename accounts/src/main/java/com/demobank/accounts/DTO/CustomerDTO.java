package com.demobank.accounts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {

    private String name;

    private String email;

    private String mobileNumber;

    private AccountsDTO accountsDTO;
}
