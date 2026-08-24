package com.DemoBank.Cards.Controller;

import com.DemoBank.Cards.Constants.CardsConstants;
import com.DemoBank.Cards.DTO.CardsDTO;
import com.DemoBank.Cards.DTO.ErrorResponseDTO;
import com.DemoBank.Cards.DTO.ResponseDTO;
import com.DemoBank.Cards.Service.ICardsService;
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
@Tag(name = "REST APIs for Cards service of DemoBank",
description = "REST API docs of CREATE, READ, UPDATE, and DELETE operations for Cards service of DemoBank")
public class CardsController {

    private ICardsService iCardsService;

    @Operation(summary = "CREATE REST API",
            description = "REST API to create a new Card for a Customer in DemoBank")
    @ApiResponse(description = "HTTP Status Created",
            responseCode = "201")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCard(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber) {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "READ REST API",
            description = "REST API to fetch the Card details of a customer of DemoBank"
    )
    @ApiResponse(
            description = "HTTP Status OK",
            responseCode = "200"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CardsDTO> findCard(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}",message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber) {
        CardsDTO cardsDTO = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDTO);
    }

    @Operation(
            summary = "UPDATE REST API",
            description = "REST API to update a Card details of a customer of DemoBank"
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
    public  ResponseEntity<ResponseDTO> updateCardDetails(@Valid @RequestBody CardsDTO cardsDTO) {
        boolean isUpdated = false;
        isUpdated = iCardsService.updateCardDetails(cardsDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "DELETE REST API",
            description = "Rest API to delete a Card of customer of DemoBank"
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
    public ResponseEntity<ResponseDTO> deleteCard(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
            @RequestParam
            String mobileNumber) {
        boolean isDeleted = false;
        isDeleted = iCardsService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }
}
