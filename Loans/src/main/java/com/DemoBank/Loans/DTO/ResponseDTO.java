package com.DemoBank.Loans.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ResponseDTO {

    @Schema(description = "HTTP Status Code returned for the operation", example = "200")
    private String statusCode;

    @Schema(description = "Message returned for the operation", example = "Request processed successfully")
    private String statusMsg;

}
