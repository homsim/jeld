package com.homsim.jeld.service;

import java.util.List;

import com.google.inject.Inject;
import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.FinanceEntry;
import jakarta.persistence.EntityManagerFactory;

/**
 * Service-layer for the import of data.
 */
public class DataImporterService {
    protected final EntityManagerFactory emf;
    @Inject
    private BankAccountService bankAccountService;

    @Inject
    public DataImporterService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Persist imported data
     */
    public void saveImportedData(BankAccount bankAccount, List<FinanceEntry> financeEntries) {
        /*
        - For all entities, check if they already exist (they do not yet have an ID, so check according to appropriate fields)
        - Create bankAccount and FinanceTimeSeries if not yet existent
         */
        emf.runInTransaction(em -> {
            if (bankAccountService.findByIban(bankAccount.getIban()) == null) {
                em.persist(bankAccount);
            }
            //... I need to rework the data structure first and think about relation-ownership
        });
    }
}
