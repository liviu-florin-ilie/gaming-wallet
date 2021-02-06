package com.gaming.wallet.event;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreditedMoneyEvent implements MoneyEvent{
    private final String transactionId;
    private final String walletOwnerId;
    private final BigDecimal amount;
}
