package com.gaming.wallet.axon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @Column(unique = true)
    private String id;
    private String walletId;
    private TransactionType type;
    private Timestamp timestamp;
    private BigDecimal amount;

    public Transaction(String id) {
        this.id = id;
    }
}
