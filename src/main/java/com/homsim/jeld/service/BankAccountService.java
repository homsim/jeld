package com.homsim.jeld.service;


import com.google.inject.Inject;
import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.BankAccountRole;
import jakarta.persistence.EntityManagerFactory;

public class BankAccountService extends DatabaseBaseService<BankAccount> {
    @Inject
    public BankAccountService(EntityManagerFactory emf) {
        super(emf, BankAccount.class);
    }

    /*
    Get all BankAccounts that are the user's bank accounts and not counterparties.

    using an SQL statement like SELECT * FROM bank_account WHERE BankAccountRole == USERACCOUNT
     */
    public BankAccount getAllUserBankAccounts() {
        return new BankAccount("", "", "", BankAccountRole.USERACCOUNT);
        // ToDo
    }
}