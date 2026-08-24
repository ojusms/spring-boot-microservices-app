package com.DemoBank.Loans.Repository;

import com.DemoBank.Loans.Entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Integer> {

    Optional<Loans> findByMobileNumber(String mobileNumber);
}
