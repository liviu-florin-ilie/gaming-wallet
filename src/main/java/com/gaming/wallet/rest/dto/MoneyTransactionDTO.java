package com.gaming.wallet.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MoneyTransactionDTO {
    private BigDecimal amount;
    private String transactionId;
}
