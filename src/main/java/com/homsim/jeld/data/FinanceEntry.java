package com.homsim.jeld.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CompositeType;
import org.javamoney.moneta.Money;

@Data
@NoArgsConstructor
@Entity
@Table(name = "finance_entry")
public class FinanceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, name = "date_time")
    private LocalDateTime dateTime;

    @CompositeType(value = MoneyUserType.class)
    private Money balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finance_time_series_id", nullable = false)
    private FinanceTimeSeries financeTimeSeries;

    @OneToMany(mappedBy = "financeEntry")
    private Set<Spending> spendings = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public FinanceEntry(LocalDateTime dateTime, Money balance, Set<Spending> spendings) {
        this.dateTime = dateTime;
        this.balance = balance;
        this.spendings = spendings;
    }
}
