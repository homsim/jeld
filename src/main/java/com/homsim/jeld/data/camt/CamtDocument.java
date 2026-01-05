package com.homsim.jeld.data.camt;

import java.util.List;

import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.FinanceEntry;
import com.homsim.jeld.data.Spending;

/**
 * Interface to unify all CAMT Documents
 */
public interface CamtDocument {
    BankAccount parseUserBankAccount();
    List<Spending> parseSpendings();
    FinanceEntry parseFinanceEntry();
}