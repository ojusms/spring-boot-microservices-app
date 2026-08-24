package com.DemoBank.Loans.Controller;

import com.DemoBank.Loans.Constants.LoansConstants;
import com.DemoBank.Loans.DTO.ErrorResponseDTO;
import com.DemoBank.Loans.DTO.LoansDTO;
import com.DemoBank.Loans.DTO.ResponseDTO;
import com.DemoBank.Loans.Entity.Loans;
import com.DemoBank.Loans.Service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@Tag(name = "REST APIs for Loans service of DemoBank",
        description = "REST API docs of CREATE, READ, UPDATE, and DELETE operations for Loans service of DemoBank")
public class LoansController {

    private ILoansService iLoansService;

    @Operation(summary = "CREATE REST API",
            description = "REST API to create a new Loan for a Customer in DemoBank")
    @ApiResponse(description = "HTTP Status Created",
            responseCode = "201")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoan(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}", message = "mobileNumber must be 10 digits")
            @RequestParam String mobileNumber) {
        iLoansService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(
            summary = "READ REST API",
            description = "REST API to fetch the Loan details of a customer of DemoBank"
    )
    @ApiResponse(
            description = "HTTP Status OK",
            responseCode = "200"
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoansDTO> findLoan(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}", message = "mobileNumber must be 10 digits")
            @RequestParam
            String mobileNumber) {
        LoansDTO loansDTO = iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDTO);
    }

    @Operation(
            summary = "UPDATE REST API",
            description = "REST API to update a Loan's details of a customer of DemoBank"
    )
    @ApiResponses({ // use this tag for multiple possible responses in the swagger api doc ui
            @ApiResponse(
                    description = "HTTP Status OK",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "HTTP Status EXPECTATION_FAILED",
                    responseCode = "417",
                    /* since error response dto is only sent from the
                    exception handler, we have to give the reference like this so it shows up in
                    the Swagger UI for API docs
                     */
                    content = @Content(
                            schema = @Schema(implementation= ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateLoan(@Valid @RequestBody LoansDTO loansDTO) {
        boolean isUpdated = false;
        isUpdated = iLoansService.updateLoan(loansDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "DELETE REST API",
            description = "Rest API to delete a Loan of customer of DemoBank"
    )
    @ApiResponses({
            @ApiResponse(
                    description = "HTTP Status OK",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "HTTP Status EXPECTATION_FAILED",
                    responseCode = "417"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoan(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}",message = "mobileNumber must be 10 digits")
            @RequestParam
            String mobileNumber) {
        boolean isDeleted = false;
        isDeleted = iLoansService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }
}
