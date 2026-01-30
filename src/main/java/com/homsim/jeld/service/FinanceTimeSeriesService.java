package com.homsim.jeld.service;

import java.util.List;

import com.google.inject.Inject;
import com.homsim.jeld.data.FinanceTimeSeries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class FinanceTimeSeriesService {
    protected final EntityManagerFactory emf;

    @Inject
    public FinanceTimeSeriesService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<FinanceTimeSeries> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createNamedQuery("findAll", FinanceTimeSeries.class).getResultList();
        }
    }
}
