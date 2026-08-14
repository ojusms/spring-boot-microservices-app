package com.demobank.accounts.Repository;

import com.demobank.accounts.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // declare an abstract method to find a customer by mobile number. Ensuring the method name follows proper convention.
    // JpaRepository of Spring Data JPA takes care of the rest
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
