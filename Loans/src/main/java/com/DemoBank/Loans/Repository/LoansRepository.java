package com.DemoBank.Loans.Repository;

import com.DemoBank.Loans.Entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Integer> {
}
