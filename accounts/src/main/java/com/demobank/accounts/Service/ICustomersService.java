package com.demobank.accounts.Service;

import com.demobank.accounts.DTO.CustomerDetailsDTO;

public interface ICustomersService {
    /**
     *
     * @param mobileNumber Input mobile number
     * @return {@link CustomerDetailsDTO} object
     */
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber);
}
