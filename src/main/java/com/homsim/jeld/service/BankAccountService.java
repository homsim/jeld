package com.homsim.jeld.service;


import com.google.inject.Inject;
import com.homsim.jeld.data.BankAccount;
import jakarta.persistence.EntityManagerFactory;

public class BankAccountService extends DatabaseBaseService<BankAccount> {
    @Inject
    public BankAccountService(EntityManagerFactory emf) {
        super(emf, BankAccount.class);
    }
}