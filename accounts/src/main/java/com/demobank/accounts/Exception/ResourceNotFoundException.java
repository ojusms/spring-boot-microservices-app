package com.demobank.accounts.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for when a resource is not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     *
     * @param resourceName the resource that was looked up
     * @param fieldName the parameter that was queried
     * @param fieldValue the value that was input for the query parameter
     */
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {

        super(String.format("%s not found for given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
