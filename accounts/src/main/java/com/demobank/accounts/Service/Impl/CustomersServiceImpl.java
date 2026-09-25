package com.demobank.accounts.Service.Impl;

import com.demobank.accounts.DTO.AccountsDTO;
import com.demobank.accounts.DTO.CardsDTO;
import com.demobank.accounts.DTO.CustomerDetailsDTO;
import com.demobank.accounts.DTO.LoansDTO;
import com.demobank.accounts.Entity.Accounts;
import com.demobank.accounts.Entity.Customer;
import com.demobank.accounts.Exception.ResourceNotFoundException;
import com.demobank.accounts.Mapper.AccountsMapper;
import com.demobank.accounts.Mapper.CustomerMapper;
import com.demobank.accounts.Repository.AccountsRepository;
import com.demobank.accounts.Repository.CustomerRepository;
import com.demobank.accounts.Service.Client.CardsFeignClient;
import com.demobank.accounts.Service.Client.LoansFeignClient;
import com.demobank.accounts.Service.ICustomersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId", customer.getCustomerId().toString())
        );
        // populate Customer details into CustomerDetailsDTO
        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDTO(customer, new CustomerDetailsDTO());
        // populate Accounts details into CustomerDetailsDTO
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));
        // make an API call to Cards service via FeignClient to get Cards details
        ResponseEntity<CardsDTO> cardsDTOResponseEntity = cardsFeignClient.findCard(mobileNumber);
        // populate Cards details into CustomerDetailsDTO
        customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());
        // make an API call to Loans service via FeignClient to get Loans details
        ResponseEntity<LoansDTO> loansDTOResponseEntity = loansFeignClient.findLoan(mobileNumber);
        // populate Loans details into CustomerDetailsDTO
        customerDetailsDTO.setLoansDTO(loansDTOResponseEntity.getBody());
        return customerDetailsDTO;
    }
}
