package com.demobank.accounts.Service.Impl;

import com.demobank.accounts.DTO.CustomerDetailsDTO;
import com.demobank.accounts.Repository.AccountsRepository;
import com.demobank.accounts.Repository.CustomerRepository;
import com.demobank.accounts.Service.Client.CardsFeignClient;
import com.demobank.accounts.Service.Client.LoansFeignClient;
import com.demobank.accounts.Service.ICustomersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     *
     * @param mobileNumber Input mobile number
     * @return {@link CustomerDetailsDTO} object
     */
    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber) {
        return null;
    }
}
