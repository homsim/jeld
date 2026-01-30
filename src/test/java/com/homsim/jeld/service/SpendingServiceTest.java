package com.homsim.jeld.service;

import com.homsim.jeld.data.BankAccountRole;
import com.homsim.jeld.data.Spending;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the SpendingService.
 */
public class SpendingServiceTest extends BaseServiceTest {
    /**
     * Tests the fetch of a spending via findById
     */
    @Test
    public void testFindById() {
        Spending spending = spendingService.findById(1L);
        assertAll(
                () -> assertEquals(Money.of(99.9900, "EUR"), spending.getAmount()),
                () -> assertEquals("Electronics purchase", spending.getUsage()),
                () -> assertEquals(BankAccountRole.COUNTERPARTY, spending.getCounterparty().getRole()),
                () -> assertEquals("Amazon EU", spending.getCounterparty().getName())
        );
    }
}
