package com.demobank.accounts.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Data Transfer Object pattern for transferring data between service layers in the application.
Reduces the number of requests for fetching data from different entities/db tables by making one call
and using a 'mapper' or 'assembler' to create an output object that has fields from multiple entities.
 */

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountsDTO {
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
