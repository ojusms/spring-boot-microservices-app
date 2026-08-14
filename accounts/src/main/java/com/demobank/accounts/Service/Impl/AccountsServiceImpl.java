package com.demobank.accounts.Service.Impl;

import com.demobank.accounts.Constants.AccountsConstants;
import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.Entity.Accounts;
import com.demobank.accounts.Entity.Customer;
import com.demobank.accounts.Mapper.CustomerMapper;
import com.demobank.accounts.Exception.CustomerAlreadyExistsException;
import com.demobank.accounts.Repository.AccountsRepository;
import com.demobank.accounts.Repository.CustomerRepository;
import com.demobank.accounts.Service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        return newAccount;
    }
}
