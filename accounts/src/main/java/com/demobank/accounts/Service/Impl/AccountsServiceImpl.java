package com.demobank.accounts.Service.Impl;

import com.demobank.accounts.Constants.AccountsConstants;
import com.demobank.accounts.DTO.AccountsDTO;
import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.Entity.Accounts;
import com.demobank.accounts.Entity.Customer;
import com.demobank.accounts.Exception.ResourceNotFoundException;
import com.demobank.accounts.Mapper.AccountsMapper;
import com.demobank.accounts.Mapper.CustomerMapper;
import com.demobank.accounts.Exception.CustomerAlreadyExistsException;
import com.demobank.accounts.Repository.AccountsRepository;
import com.demobank.accounts.Repository.CustomerRepository;
import com.demobank.accounts.Service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/* Following best practice of creating a separate Impl package within, and naming with postfix "Impl"
for interface implementation classes.
Lombok will build a constructor of all fields. Since there is only 1 constructor, no need to explicitly use
@Autowired annotation to autowire the fields. Spring can do so by itself in this case.
 */

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    // fields required for interacting with DB via Repository layer
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     *
     * @param customerDTO CustomerDTO Object
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        // use the Mapper class to populate Customer Entity from Customer DTO
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        // update to check if an existing customer exists with the same number and throw an exception
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with mobileNumber "
            + customerDTO.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        // save the customer to DB and access the returned customer object for the Customer ID
        Customer savedCustomer = customerRepository.save(customer);
        // call the helper method to create an account for the new customer and save it in the DB
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     * Helper method to create an Account for a Customer
     * @param customer Customer
     * @return Accounts
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        // generate a random account number of 10 digits
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    /**
     *
     * @param mobileNumber input mobile number to look up customer by
     * @return CustomerDTO object containing customer details
     */
    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        // if customer found, return Customer obj, else calls Optional<>.orElseThrow() to throw
        // not found exception
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        // if customer found, we want to find the account table entry having the customer ID
        // value and get the details. Update AccountsRepository with a method to find by Cust ID and use it.
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId", customer.getCustomerId().toString())
        );
        // now we found Customer and Account details but we return CustomerDTO which consists of only Customer details.
        // So we update the CustomeDTO with a new field of AccountsDTO type so the returned CustomerDTO has account info also.
        // Map the found Customer and Accounts to their respective DTO with Mapper to show selective info
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));
        return customerDTO;
    }

    /**
     *
     * @param customerDTO input customer details to update
     * @return boolean value for success or fail
     */
    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountsDTO accountsDTO = customerDTO.getAccountsDTO();
        if (accountsDTO!=null) {
            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber",accountsDTO.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDTO, accounts);
            accountsRepository.save(accounts);
            Customer customer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer","mobileNumber", customerDTO.getMobileNumber())
            );
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

}
