package com.demobank.accounts.Service.Impl;

import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.Repository.AccountsRepository;
import com.demobank.accounts.Repository.CustomerRepository;
import com.demobank.accounts.Service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    }
}
