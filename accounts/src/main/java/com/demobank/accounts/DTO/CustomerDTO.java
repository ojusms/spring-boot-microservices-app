package com.demobank.accounts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CustomerDTO {

    private String name;

    private String email;

    private Long mobileNumber;
}
