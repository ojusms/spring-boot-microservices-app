package com.DemoBank.Loans.Controller;

import com.DemoBank.Loans.Constants.LoansConstants;
import com.DemoBank.Loans.DTO.LoansDTO;
import com.DemoBank.Loans.DTO.ResponseDTO;
import com.DemoBank.Loans.Entity.Loans;
import com.DemoBank.Loans.Service.ILoansService;
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
public class LoansController {

    private ILoansService iLoansService;

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

    @GetMapping("/fetch")
    public ResponseEntity<LoansDTO> findLoan(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}", message = "mobileNumber must be 10 digits")
            @RequestParam
            String mobileNumber) {
        LoansDTO loansDTO = iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDTO);
    }

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
