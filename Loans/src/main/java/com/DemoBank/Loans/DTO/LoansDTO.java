package com.DemoBank.Loans.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Schema(
        name = "Loans",
        description = "Schema for Loans of DemoBank"
)
public class LoansDTO {

    @NotEmpty(message = "Mobile Number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "mobileNumber must be 10 digits")
    @Schema(
            description = "Mobile number of Customer of DemoBank",
            example = "7896541230"
    )
    private String mobileNumber;

    @NotEmpty(message = " Loan number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "loanNumber must be 12 digits")
    @Schema(
            description = "Loan Number of the Loan of Customer of DemoBank",
            example = "987456321753"
    )
    private String loanNumber;

    @NotEmpty(message = "Loan type cannot be null or empty")
    @Schema(
            description = "Type of Loan",
            example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan amount must be greater than 0")
    @Schema(
            description = "Total amount for the Loan",
            example = "100000"
    )
    private int totalLoan;

    @PositiveOrZero(message = "Loan amount paid must be greater than or equal to 0")
    @Schema(
            description = "Current amount repaid out of the total amount borrowed for the Loan",
            example = "25000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Outstanding loan amount must be greater than or equal to 0")
    @Schema(
            description = "Outstanding amount to be repaid out of total amount borrowed for the Loan",
            example = "75000"
    )
    private int outstandingAmount;
}
