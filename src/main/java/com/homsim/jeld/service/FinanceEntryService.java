package com.homsim.jeld.service;

import com.google.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

public class FinanceEntryService {
    protected final EntityManagerFactory emf;

    @Inject
    public FinanceEntryService(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
