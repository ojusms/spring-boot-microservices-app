package com.DemoBank.Loans.Mapper;

import com.DemoBank.Loans.DTO.LoansDTO;
import com.DemoBank.Loans.Entity.Loans;

/**
 * Mapper class with methods to map data between Entity class and DTO class
 */
public class LoansMapper {
    /**
     * take values from input Loans object and populate into input LoansDTO object and return
     * @param loans input Loans object
     * @param loansDTO input LoansDTO object to map values and return
     * @return LoansDTO object mapped with values from input Loans object
     */
    public static LoansDTO mapToLoansDTO(Loans loans, LoansDTO loansDTO) {
        loansDTO.setLoanNumber(loans.getLoanNumber());
        loansDTO.setLoanType(loans.getLoanType());
        loansDTO.setMobileNumber(loans.getMobileNumber());
        loansDTO.setTotalLoan(loans.getTotalLoan());
        loansDTO.setAmountPaid(loans.getAmountPaid());
        loansDTO.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDTO;
    }

    /**
     * take values from LoansDTO object and populate into Loans object and return
     * @param loansDTO input LoansDTO object
     * @param loans input Loans Object to map values and return
     * @return Loans object mapped with values from input LoansDTO object
     */
    public static Loans mapToLoans(LoansDTO loansDTO, Loans loans) {
        loans.setLoanNumber(loansDTO.getLoanNumber());
        loans.setLoanType(loansDTO.getLoanType());
        loans.setMobileNumber(loansDTO.getMobileNumber());
        loans.setTotalLoan(loansDTO.getTotalLoan());
        loans.setAmountPaid(loansDTO.getAmountPaid());
        loans.setOutstandingAmount(loansDTO.getOutstandingAmount());
        return loans;
    }
}
