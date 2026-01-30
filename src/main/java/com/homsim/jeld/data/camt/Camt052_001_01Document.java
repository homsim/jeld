package com.homsim.jeld.data.camt;

import java.util.HashSet;
import java.util.Set;

import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.BankAccountRole;
import com.homsim.jeld.data.FinanceEntry;
import com.homsim.jeld.data.Spending;
import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;

@AllArgsConstructor
public class Camt052_001_01Document implements CamtDocument {
    private com.homsim.jeld.data.parse.camt_052_001_01.Document doc;

    public BankAccount parseUserBankAccount() {
        return new BankAccount(
                this.doc.getBkToCstmrAcctRptV01().getRpt().getFirst()
                        .getAcct().getId().getIBAN(),
                this.doc.getBkToCstmrAcctRptV01().getRpt().getFirst()
                        .getAcct().getSvcr().getFinInstnId().getBIC(),
                this.doc.getBkToCstmrAcctRptV01().getRpt().getFirst()
                        .getAcct().getSvcr().getBrnchId().getNm(),
                BankAccountRole.USERACCOUNT
        );
    }

    public FinanceEntry parseFinanceEntry() {
        return new FinanceEntry(
                this.doc.getBkToCstmrAcctRptV01().getRpt().getFirst().getBal().getLast().getDt().getDt().toGregorianCalendar().toZonedDateTime().toLocalDateTime(),
                Money.of(this.doc.getBkToCstmrAcctRptV01().getRpt().getFirst().getBal().getLast().getAmt().getValue(), this.doc.getBkToCstmrAcctRptV01().getRpt().getFirst().getBal().getLast().getAmt().getCcy()),
                this.parseSpendings()
        );
    }

    public Set<Spending> parseSpendings() {
        Set<Spending> spendings = new HashSet<>();
        this.doc.getBkToCstmrAcctRptV01().getRpt().getFirst().getNtry().forEach(
                ntry -> spendings.add(
                        new Spending(
                                Money.of(ntry.getAmt().getValue(), ntry.getAmt().getCcy()),
                                ntry.getTxDtls().getFirst().getRmtInf().getUstrd().getFirst(),
                                new BankAccount(
                                        ntry.getTxDtls().getFirst().getRltdPties().getCdtrAcct().getId().getIBAN(),
                                        ntry.getTxDtls().getFirst().getRltdAgts().getCdtrAgt().getFinInstnId().getBIC(),
                                        ntry.getTxDtls().getFirst().getRltdPties().getCdtr().getNm(),
                                        BankAccountRole.COUNTERPARTY
                                )
                        )
                )
        );
        return spendings;
    }
}
