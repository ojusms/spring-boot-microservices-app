package com.demobank.accounts.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
        name = "Response", description = "Schema for an API Response for Accounts service APIs in DemoBank"
)
public class ResponseDTO {
    @Schema(description = "HTTP Status Code returned for the operation", example = "200")
    private String statusCode;

    @Schema(description = "Message returned for the operation", example = "Request processed successfully")
    private String statusMsg;
}
