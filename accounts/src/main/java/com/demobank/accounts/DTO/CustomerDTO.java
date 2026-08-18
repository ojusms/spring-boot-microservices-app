package com.demobank.accounts.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Schema(
        name = "Customer",
        description = "Schema for Customer of DemoBank "
)
public class CustomerDTO {

    @NotNull(message = "Name cannot be null or empty")
    @Size(min = 2, max = 30, message = "Name cannot be less than 2 and greater than 30")
    @Schema(description = "Name of the Cusomer", example = "John Doe")
    private String name;

    @NotNull(message = "Email cannot be null o empty")
    @Email(message = "Email must follow correct format")
    @Schema(description = "Email of the Customer", example = "John@email.com")
    private String email;

    @NotNull(message = "Mobile Number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be only 10 digits")
    @Schema(description = "Mobile number of the Customer", example = "789564123")
    private String mobileNumber;

    private AccountsDTO accountsDTO;
}
