package com.homsim.jeld.service;

import com.google.inject.Inject;
import com.homsim.jeld.data.FinanceEntry;
import jakarta.persistence.EntityManagerFactory;

public class FinanceEntryService extends DatabaseBaseService<FinanceEntry> {
    @Inject
    public FinanceEntryService(EntityManagerFactory emf) {
        super(emf, FinanceEntry.class);
    }
}
