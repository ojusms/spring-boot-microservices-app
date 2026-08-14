package com.demobank.accounts.Constants;

// class with reusable strings

public class AccountsConstants {

     private AccountsConstants() {
         // private constructor so an instance of this class cannot be instantiated
    }

    // static and final keywords used so these constants cannot be changed during runtime
    // and are accessible to the class directly
    private static final String SAVINGS = "Savings";
    private static final String ADDRESS = "123 Main St, New York";
    private static final String STATUS_201 = "201";
    private static final String MESSAGE_201 = "Account created successfully";
    private static final String STATUS_200 = "200";
    private static final String MESSAGE_200 = "Request processed successfully";
    private static final String STATUS_500 = "500";
    private static final String MESSAGE_500 = "An error occurred. Please try again";
}
