package com.homsim.jeld.service;


import java.util.List;

import com.google.inject.Inject;
import com.homsim.jeld.data.BankAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class BankAccountService {
    protected final EntityManagerFactory emf;

    @Inject
    public BankAccountService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public BankAccount findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(BankAccount.class, id);
        }
    }

    public void update(BankAccount bankAccount) {
        emf.runInTransaction(em -> em.merge(bankAccount));
    }

    /**
     *  Get all BankAccounts that are the user's bank accounts and not counterparties.
     */
    public List<BankAccount> getAllUserBankAccounts() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("getUserAccounts", BankAccount.class).getResultList();
    }
}