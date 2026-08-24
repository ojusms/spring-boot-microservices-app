package com.DemoBank.Loans.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ErrorResponseDTO {

    @Schema(description = "API path invoked by client")
    private String apiPath;

    @Schema(description = "Error code representing the error")
    private HttpStatus errorCode;

    @Schema(description = "Error message for the error")
    private String errorMessage;

    @Schema(description = "Date and Time when the error happened")
    private LocalDateTime errorDate;
}
