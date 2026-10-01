package com.demobank.accounts.Service;

import com.demobank.accounts.DTO.CustomerDetailsDTO;

public interface ICustomersService {
    /**
     *
     * @param mobileNumber  Input mobile number
     * @param correlationId Correlation ID for the request from API Gateway
     * @return {@link CustomerDetailsDTO} object
     */
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId);
}
