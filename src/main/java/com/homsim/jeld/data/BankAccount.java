package com.homsim.jeld.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
A bank account that has finance data entries attached to it.
 */
@Data
@NoArgsConstructor
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iban;
    private  String bic;
    private String name;
    private BankAccountRole role;

    public BankAccount(String iban, String bic, String name, BankAccountRole role) {
        this.iban = iban;
        this.bic = bic;
        this.name = name;
        this.role = role;
    }
}
