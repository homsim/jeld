package com.homsim.jeld.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.homsim.jeld.service.BankAccountService;
import com.homsim.jeld.service.FinanceEntryService;
import com.homsim.jeld.service.FinanceTimeSeriesService;
import com.homsim.jeld.service.SpendingService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseModule extends AbstractModule {
    private final EntityManagerFactory emf;

    public DatabaseModule() {
        this.emf = Persistence.createEntityManagerFactory("com.homsim.jeld");
    }

    @Override
    protected void configure() {
        // Bind the EMF as a singleton
        bind(EntityManagerFactory.class).toInstance(emf);

        // Services are singletons
        bind(BankAccountService.class).in(Scopes.SINGLETON);
        bind(FinanceEntryService.class).in(Scopes.SINGLETON);
        bind(FinanceTimeSeriesService.class).in(Scopes.SINGLETON);
        bind(SpendingService.class).in(Scopes.SINGLETON);
    }

}
