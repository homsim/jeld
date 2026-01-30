package com.homsim.jeld.data.camt;

import java.util.HashSet;
import java.util.Set;

import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.BankAccountRole;

import com.homsim.jeld.data.FinanceEntry;
import com.homsim.jeld.data.Spending;
import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;

/**
 * Only temporary to find common properties in the CAMT(52) standard
 */
@AllArgsConstructor
public class Camt052_001_06Document implements CamtDocument {
    private com.homsim.jeld.data.parse.camt_052_001_06.Document doc;

    public BankAccount parseUserBankAccount() {
        return new BankAccount(
                this.doc.getBkToCstmrAcctRpt().getRpt().getFirst()
                        .getAcct().getId().getIBAN(),
                this.doc.getBkToCstmrAcctRpt().getRpt().getFirst()
                        .getAcct().getSvcr().getFinInstnId().getBICFI(),
                this.doc.getBkToCstmrAcctRpt().getRpt().getFirst()
                        .getAcct().getSvcr().getFinInstnId().getNm(),
                BankAccountRole.USERACCOUNT
        );
    }

    public FinanceEntry parseFinanceEntry() {
        return new FinanceEntry(
                this.doc.getBkToCstmrAcctRpt().getRpt().getFirst().getBal().getLast().getDt().getDt().toGregorianCalendar().toZonedDateTime().toLocalDateTime(),
                Money.of(this.doc.getBkToCstmrAcctRpt().getRpt().getFirst().getBal().getLast().getAmt().getValue(), this.doc.getBkToCstmrAcctRpt().getRpt().getFirst().getBal().getLast().getAmt().getCcy()),
                this.parseSpendings()
        );
    }

    public Set<Spending> parseSpendings() {
        Set<Spending> spendings = new HashSet<>();
        this.doc.getBkToCstmrAcctRpt().getRpt().getFirst().getNtry().forEach(
                ntry -> spendings.add(
                        new Spending(
                                Money.of(ntry.getAmt().getValue(), ntry.getAmt().getCcy()),
                                ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRmtInf().getUstrd().getFirst(),
                                new BankAccount(
                                        ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRltdPties().getCdtrAcct().getId().getIBAN(),
                                        ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRltdAgts().getDbtrAgt().getFinInstnId().getBICFI(),
                                        ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRltdPties().getCdtr().getNm(),
                                        BankAccountRole.COUNTERPARTY
                                )

                        )
                )
        );
        return spendings;
    }
}
