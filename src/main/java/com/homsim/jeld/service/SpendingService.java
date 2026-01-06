package com.homsim.jeld.service;

import com.google.inject.Inject;
import com.homsim.jeld.data.Spending;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class SpendingService {
    protected final EntityManagerFactory emf;

    @Inject
    public SpendingService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Spending findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Spending.class, id);
        }
    }
}
