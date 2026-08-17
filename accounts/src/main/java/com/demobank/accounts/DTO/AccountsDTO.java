package com.demobank.accounts.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Data Transfer Object pattern for transferring data between service layers in the application.
Reduces the number of requests for fetching data from different entities/db tables by making one call
and using a 'mapper' or 'assembler' to create an output object that has fields from multiple entities.
 */

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountsDTO {
    @NotNull(message = "Account Number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Account Number must be only 10 digits")
    private Long accountNumber;

    @NotNull(message = "Account Type cannot be null or empty")
    private String accountType;

    @NotNull(message = "Branch Address cannot be null or empty")
    private String branchAddress;
}
