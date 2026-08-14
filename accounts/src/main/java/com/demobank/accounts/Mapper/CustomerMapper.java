package com.demobank.accounts.Mapper;

import com.demobank.accounts.DTO.CustomerDTO;
import com.demobank.accounts.Entity.Customer;
/**
 Mapper class with methods to map data between Entity class and DTO class.
 */
public class CustomerMapper {
    /**
     * take info from Customer object and populate into CustomerDTO object
     * @param customer Customer Object
     * @param customerDTO CustomerDTO Object
     * @return CustomerDTO Object
     */
    public static CustomerDTO mapToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        return customerDTO;
    }

    /**
     * take info from CustomerDTO object and populate into Customer object
     * @param customer Customer Object
     * @param customerDTO CustomerDTO Object
     * @return Customer Object
     */
    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer) {
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        return customer;
    }
}
