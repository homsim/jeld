package com.homsim.jeld.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.homsim.jeld.TestDatabaseModule;
import com.homsim.jeld.data.Spending;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * Test all the services' fetch methods with unified test-data.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseServiceTest {
    Path resourceDirectory = Paths.get("src", "test", "resources", "servicesetup");

    EntityManagerFactory emf;
    BankAccountService bankAccountService;
    SpendingService spendingService;

    @BeforeAll
    void setUpOnce() {
        Injector injector = Guice.createInjector(new TestDatabaseModule());
        this.emf = injector.getInstance(EntityManagerFactory.class);
        this.bankAccountService = injector.getInstance(BankAccountService.class);
        this.spendingService = injector.getInstance(SpendingService.class);
        // add the other services when required

        // execute queries to populate the test-database
        emf.runInTransaction(em -> {
            try {
                String sql = Files.readString(
                        new File(resourceDirectory.toFile(), "serviceTestSetup.sql").toPath()
                );

                for (String statement : sql.split(";")) {
                    if (!statement.trim().isEmpty()) {
                        em.createNativeQuery(statement).executeUpdate();
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterAll
    void cleanup() {
        // not sure if needed, but better do it
        emf.close();
    }
}
