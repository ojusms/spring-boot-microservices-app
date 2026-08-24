package com.DemoBank.Cards.DTO;

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
        name = "Cards",
        description = "Schema for Cards of DemoBank"
)
public class CardsDTO {

    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "mobileNumber must be 10 digits")
    @Schema(
            description = "Mobile number of Customer of DemoBank",
            example = "7896541230"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "cardNumber must be 12 digits")
    @Schema(
            description = "Card Number of the Card of Customer of DemoBank",
            example = "987456321753"
    )
    private String cardNumber;

    @NotEmpty(message = "Card type cannot be null or empty")
    @Schema(
            description = "Type of Card",
            example = "Credit Card"
    )
    private String cardType;

    @Positive(message = "Total limit must be greater than 0")
    @Schema(
            description = "Total limit for the Card",
            example = "100000"
    )
    private int totalLimit;

    @PositiveOrZero(message = "Amount used must be greater than or equal to 0")
    @Schema(
            description = "Current amount used out of the total limit for the Card",
            example = "25000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Available amount must be greater than or equal to 0")
    @Schema(
            description = "Current amount available to use from the total limit",
            example = "75000"
    )
    private int availableAmount;

}
