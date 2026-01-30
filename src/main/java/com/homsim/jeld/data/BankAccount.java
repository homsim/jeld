package com.homsim.jeld.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bank_account")
@NamedQuery(name = "getUserAccounts", query = "SELECT ba FROM BankAccount ba WHERE ba.role = com.homsim.jeld.data.BankAccountRole.USERACCOUNT")
@NamedQuery(name = "findByIban", query = "SELECT ba FROM BankAccount ba WHERE ba.iban = :iban")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 34)
    private String iban;
    @Column(nullable = false, length = 12)
    private  String bic;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private BankAccountRole role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "bankAccount")
    private Set<FinanceTimeSeries> bankAccountFinanceTimeSeries = new HashSet<>();

    @OneToMany(mappedBy = "counterparty")
    private Set<Spending> counterpartySpendings = new HashSet<>();

    public BankAccount(String iban, String bic, String name, BankAccountRole role) {
        this.iban = iban;
        this.bic = bic;
        this.name = name;
        this.role = role;
    }
}
/*
A bank account that has finance data entries attached to it.
 */
