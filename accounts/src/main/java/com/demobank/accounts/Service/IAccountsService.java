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

    /**
     *
     * @param mobileNumber input mobile number to look up customer by
     * @return CustomerDTO object containing customer details
     */
    CustomerDTO fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDTO input customer details to update
     * @return boolean value for success or fail
     */
    boolean updateAccount(CustomerDTO customerDTO);

    /**
     *
     * @param mobileNumber input mobile number to delete account by
     * @return boolean value for success or fail
     */
    boolean deleteAccount(String mobileNumber);
}
