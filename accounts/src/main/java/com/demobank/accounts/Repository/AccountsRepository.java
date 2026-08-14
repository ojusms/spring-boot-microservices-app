package com.demobank.accounts.Repository;

import com.demobank.accounts.Entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    // declare an abstract method to find an account by Customer ID. Ensuring the method name follows proper convention.
    // JpaRepository of Spring Data JPA takes care of the rest
    Optional<Accounts> findByCustomerId(long customerId);
}
