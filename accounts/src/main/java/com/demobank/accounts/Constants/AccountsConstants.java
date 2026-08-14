package com.demobank.accounts.Constants;

// class with reusable strings

public class AccountsConstants {

     private AccountsConstants() {
         // private constructor so an instance of this class cannot be instantiated
    }

    // static and final keywords used so these constants cannot be changed during runtime
    // and are accessible to the class directly
    public static final String SAVINGS = "Savings";
    public static final String ADDRESS = "123 Main St, New York";
    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Account created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "An error occurred. Please try again";
}
