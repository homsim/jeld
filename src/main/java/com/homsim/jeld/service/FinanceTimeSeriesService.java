package com.homsim.jeld.service;

import com.google.inject.Inject;
import com.homsim.jeld.data.FinanceTimeSeries;
import jakarta.persistence.EntityManagerFactory;

public class FinanceTimeSeriesService extends DatabaseBaseService<FinanceTimeSeries> {
    @Inject
    public FinanceTimeSeriesService(EntityManagerFactory emf) {
        super(emf, FinanceTimeSeries.class);
    }

}
