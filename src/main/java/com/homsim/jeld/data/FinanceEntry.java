package com.homsim.jeld.data;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

@Data
@NoArgsConstructor
@Entity
public class FinanceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private Money balance;
    @ManyToOne
    private List<Spending> spendings;

    public FinanceEntry(LocalDateTime dateTime, Money balance, List<Spending> spendings) {
        this.dateTime = dateTime;
        this.balance = balance;
        this.spendings = spendings;
    }
}
