package com.demobank.accounts.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema( // enhance the Schema section of the Swagger UI for API docs
        name = "Accounts", // override the name displayed in the Schema section of Swagger UI for API docs
        description = "Schema for Accounts of DemoBank"
)
public class AccountsDTO {
    @NotNull(message = "Account Number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Account Number must be only 10 digits")
    @Schema( // displaying same field name is fine, no need to override the name displayed in the Swagger UI
            description = "Account number in DemoBank", example = "1234567890"
    )
    private Long accountNumber;

    @NotNull(message = "Account Type cannot be null or empty")
    @Schema(
            description = "Type of Account in DemoBank", example = "Savings"
    )
    private String accountType;

    @NotNull(message = "Branch Address cannot be null or empty")
    @Schema(
            description = "The address of the DemoBank branch"
    )
    private String branchAddress;
}
