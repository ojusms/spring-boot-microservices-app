package com.demobank.accounts.Service;

import com.demobank.accounts.DTO.CustomerDTO;

/* following best practice of starting interface name with 'I'. Same is not done for
interfaces in Repository package because they do not have any implementation.
 */

public interface IAccountsService {

    // adding a JavaDoc comment for documentation. A small description can also be provided on what this method does.
    /**
     *
     * @param customerDTO CustomerDTO Object
     */
    void createAccount(CustomerDTO customerDTO);
}
