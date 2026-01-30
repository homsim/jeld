package com.homsim.jeld.service;

import java.util.List;

import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.BankAccountRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the BankAccountService.
 */
public class BankAccountServiceTest extends BaseServiceTest {
    /**
     * Tests the fetch of a single USERACCOUNT bank account via findByIban.
     */
    @Test
    public void testFindByIbanUserAccount() {
        BankAccount bankAccount = bankAccountService.findByIban("DE89370400440532013000");
        assertAll(
                () -> assertEquals("DE89370400440532013000", bankAccount.getIban()),
                () -> assertEquals("John Doe Main Account", bankAccount.getName()),
                () -> assertEquals("COBADEFFXXX", bankAccount.getBic()),
                () -> assertEquals(BankAccountRole.USERACCOUNT, bankAccount.getRole())
        );
    }

    /**
     * Tests the fetch of a single COUNTERPARTY bank account via findByIban.
     */
    @Test
    public void testFindByIbanCounterparty() {
        BankAccount bankAccount = bankAccountService.findByIban("DE44500105175407324931");
        assertAll(
                () -> assertEquals("DE44500105175407324931", bankAccount.getIban()),
                () -> assertEquals("Amazon EU", bankAccount.getName()),
                () -> assertEquals("INGDDEFFXXX", bankAccount.getBic()),
                () -> assertEquals(BankAccountRole.COUNTERPARTY, bankAccount.getRole())
        );
    }

    /**
     * Tests the fetch of bank accounts using getAllUserBankAccounts.
     */
    @Test
    public void testGetAllUserBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getAllUserBankAccounts();
        assertEquals(1, bankAccounts.size());

        assertAll(
                () -> assertEquals("DE89370400440532013000", bankAccounts.getFirst().getIban()),
                () -> assertEquals("John Doe Main Account", bankAccounts.getFirst().getName()),
                () -> assertEquals("COBADEFFXXX", bankAccounts.getFirst().getBic()),
                () -> assertEquals(BankAccountRole.USERACCOUNT, bankAccounts.getFirst().getRole())
        );
    }

}
