package com.homsim.jeld.data.camt;

import java.util.ArrayList;
import java.util.List;

import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.BankAccountRole;
import com.homsim.jeld.data.FinanceEntry;
import com.homsim.jeld.data.Spending;
import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;

@AllArgsConstructor
public class Camt052_001_13Document implements CamtDocument {
    private com.homsim.jeld.data.parse.camt_052_001_13.Document doc;

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

    public List<Spending> parseSpendings() {
        List<Spending> spendings = new ArrayList<>();
        this.doc.getBkToCstmrAcctRpt().getRpt().getFirst().getNtry().forEach(
                ntry -> spendings.addLast(
                        new Spending(
                                Money.of(ntry.getAmt().getValue(), ntry.getAmt().getCcy()),
                                ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRmtInf().getUstrd().getFirst(),
                                new BankAccount(
                                        ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRltdPties().getCdtrAcct().getId().getIBAN(),
                                        ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRltdAgts().getDbtrAgt().getFinInstnId().getBICFI(),
                                        ntry.getNtryDtls().getFirst().getTxDtls().getFirst().getRltdPties().getCdtr().getPty().getNm(),
                                        BankAccountRole.COUNTERPARTY
                                )

                        )
                )
        );
        return spendings;
    }
}
