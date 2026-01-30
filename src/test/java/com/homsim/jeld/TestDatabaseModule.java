package com.homsim.jeld;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.homsim.jeld.service.BankAccountService;
import com.homsim.jeld.service.DataImporterService;
import com.homsim.jeld.service.FinanceEntryService;
import com.homsim.jeld.service.FinanceTimeSeriesService;
import com.homsim.jeld.service.SpendingService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * Define the database module to use in tests. Overwrites the H2 database to be in-memory instead of file-based.
 * Apart from that all the configurations from the actual persistence.xml are used.
 */
public class TestDatabaseModule extends AbstractModule {

    private final EntityManagerFactory emf;

    public TestDatabaseModule() {
        Map<String, Object> props = new HashMap<>();

        // Override JDBC URL to use in-memory H2 for testing
        props.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        this.emf = Persistence.createEntityManagerFactory("com.homsim.jeld", props);
    }

    @Override
    protected void configure() {
        bind(EntityManagerFactory.class).toInstance(emf);

        // Bind services
        bind(BankAccountService.class).in(Scopes.SINGLETON);
        bind(FinanceEntryService.class).in(Scopes.SINGLETON);
        bind(FinanceTimeSeriesService.class).in(Scopes.SINGLETON);
        bind(SpendingService.class).in(Scopes.SINGLETON);
        bind(DataImporterService.class).in(Scopes.SINGLETON);
    }
}
