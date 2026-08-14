package com.demobank.accounts.Mapper;

import com.demobank.accounts.DTO.AccountsDTO;
import com.demobank.accounts.Entity.Accounts;

/**
Mapper class with methods to map data between Entity class and DTO class.
 */
public class AccountsMapper {

    /**
     * take info from Accounts object and populate into AccountsDTO object
     * @param accounts Accounts Object
     * @param accountsDTO AccountsDTO Object
     * @return AccountsDTO object
     */
    public static AccountsDTO mapToAccountsDTO(Accounts accounts, AccountsDTO accountsDTO) {

        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());

        return accountsDTO;
    }

    /**
     * take info from AccountsDTO object and populate into Accounts object
     * @param accountsDTO AccountsDTO Object
     * @param accounts Accounts Object
     * @return Accounts Object
     */
    public static Accounts mapToAccounts(AccountsDTO accountsDTO, Accounts accounts) {
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;
    }
}
