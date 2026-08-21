package com.DemoBank.Cards.Controller;

import com.DemoBank.Cards.Constants.CardsConstants;
import com.DemoBank.Cards.DTO.CardsDTO;
import com.DemoBank.Cards.DTO.ResponseDTO;
import com.DemoBank.Cards.Service.ICardsService;
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
public class CardsController {

    private ICardsService iCardsService;

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

    @GetMapping("/fetch")
    public ResponseEntity<CardsDTO> findCard(
            @Valid
            @Pattern(regexp = "^$|[0-9]{10}",message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber) {
        return null;
    }
}
