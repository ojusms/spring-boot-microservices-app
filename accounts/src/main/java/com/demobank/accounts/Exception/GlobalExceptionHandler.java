package com.demobank.accounts.Exception;

import com.demobank.accounts.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/* global exception handler class. It tells Spring to run
the methods of this class when a Controller class throws an exception
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    // tells Spring to run this method when CustomerAlreadyExistsException is thrown
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException exception, WebRequest webRequest) {
        // create ErrorResponseDTO object with info from exception and webRequest
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
