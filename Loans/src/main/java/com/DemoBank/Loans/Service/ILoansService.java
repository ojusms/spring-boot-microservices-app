package com.DemoBank.Loans.Service;

import com.DemoBank.Loans.DTO.LoansDTO;

public interface ILoansService {
    /**
     *
     * @param mobileNumber input mobile number of customer
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber input mobile number of customer
     * @return loan details for a given mobile number
     */
    LoansDTO fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDTO input loan details
     * @return bolean value for success or fail
     */
    boolean updateLoan(LoansDTO loansDTO);
}
