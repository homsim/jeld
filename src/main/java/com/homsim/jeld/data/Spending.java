package com.homsim.jeld.data;


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
public class Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Money amount;
    private String usage;
    @ManyToOne
    private BankAccount counterparty;
}
