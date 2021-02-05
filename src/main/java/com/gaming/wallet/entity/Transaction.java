package com.gaming.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.EntityId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

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
