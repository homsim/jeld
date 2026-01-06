package com.homsim.jeld.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@NamedQuery(name = "getUserAccounts", query = "SELECT * FROM BankAccount WHERE role = USERACCOUNT")
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
/*
A bank account that has finance data entries attached to it.
 */
