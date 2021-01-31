package com.gaming.wallet.event;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class DebitedMoneyEvent {
    private final String walletOwnerId;
    private final String transactionId;
    private final BigDecimal amount;
}
