package com.DemoBank.Loans.Service.Impl;

import com.DemoBank.Loans.Constants.LoansConstants;
import com.DemoBank.Loans.DTO.LoansDTO;
import com.DemoBank.Loans.Entity.Loans;
import com.DemoBank.Loans.Exception.LoanAlreadyExistsException;
import com.DemoBank.Loans.Exception.ResourceNotFoundException;
import com.DemoBank.Loans.Mapper.LoansMapper;
import com.DemoBank.Loans.Repository.LoansRepository;
import com.DemoBank.Loans.Service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;
    /**
     *
     * @param mobileNumber input mobile number of customer
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if (loans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists for mobileNumber: " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * helper method to create a new Loan for a given mobile number
     * @param mobileNumber input mobile number of the customer
     * @return Loans object with populated details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        newLoan.setMobileNumber(mobileNumber);
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setCreatedAt(LocalDateTime.now());
        newLoan.setCreatedBy("Loans_MS");
        return newLoan;
    }

    /**
     *
     * @param mobileNumber input mobile number of customer
     * @return loan details for a given mobile number
     */
    @Override
    public LoansDTO fetchLoan(String mobileNumber) {
        Loans loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );

        return LoansMapper.mapToLoansDTO(loan, new LoansDTO());
    }

    /**
     *
     * @param loansDTO input loan details
     * @return boolean value for success or fail
     */
    @Override
    public boolean updateLoan(LoansDTO loansDTO) {
        Loans loan = loansRepository.findByLoanNumber(loansDTO.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan","mobileNumber", loansDTO.getMobileNumber())
        );
        LoansMapper.mapToLoans(loansDTO,loan);
        loansRepository.save(loan);
        return true;
    }
}
