package com.DemoBank.Cards.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CardsDTO {

    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "mobileNumber must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Card number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "cardNumber must be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "Card type cannot be null or empty")
    private String cardType;

    @Positive(message = "Total limit must be greater than 0")
    private int totalLimit;

    @PositiveOrZero(message = "Amount used must be greater than or equal to 0")
    private int amountUsed;

    @PositiveOrZero(message = "Available amount must be greater than or equal to 0")
    private int availableAmount;

}
