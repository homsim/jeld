package com.homsim.jeld.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iban;
    private  String bic;

    public BankAccount(String iban, String bic) {
        this.iban = iban;
        this.bic = bic;
    }
}
