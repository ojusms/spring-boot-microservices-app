package com.DemoBank.Loans.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LoansDTO {

    @NotEmpty(message = "Mobile Number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "mobileNumber must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = " Loan number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "loanNumber must be 12 digits")
    private String loanNumber;

    @NotEmpty(message = "Loan type cannot be null or empty")
    private String loanType;

    @Positive(message = "Total loan amount must be greater than 0")
    private int totalLoan;

    @PositiveOrZero(message = "Loan amount paid must be greater than or equal to 0")
    private int amountPaid;

    @PositiveOrZero(message = "Outstanding loan amount must be greater than or equal to 0")
    private int outstandingAmount;
}
