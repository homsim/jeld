package com.homsim.jeld.service;

import com.google.inject.Inject;
import com.homsim.jeld.data.Spending;
import jakarta.persistence.EntityManagerFactory;

public class SpendingService extends DatabaseBaseService<Spending> {
    @Inject
    public SpendingService(EntityManagerFactory emf) {
        super(emf, Spending.class);
    }

}
