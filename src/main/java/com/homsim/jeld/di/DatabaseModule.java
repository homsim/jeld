package com.homsim.jeld.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.homsim.jeld.service.BankAccountService;
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
    }

}
