package com.homsim.jeld.data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CompositeType;
import org.javamoney.moneta.Money;

@Data
@NoArgsConstructor
@Entity
@Table(name = "spending")
public class Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CompositeType(value = MoneyUserType.class)
    private Money amount;

    @Column(length = 1000)
    private String usage;

    @ManyToOne()
    @JoinColumn(name = "finance_entry_id", nullable = false)
    private FinanceEntry financeEntry;

    @ManyToOne()
    @JoinColumn(name = "counterparty_id", nullable = false)
    private BankAccount counterparty;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatesAt;

    public Spending(Money amount, String usage, BankAccount counterparty) {
        this.amount = amount;
        this.usage = usage;
        this.counterparty = counterparty;
    }
}
