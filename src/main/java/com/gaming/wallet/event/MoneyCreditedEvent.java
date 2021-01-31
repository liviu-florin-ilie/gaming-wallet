package com.gaming.wallet.event;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class MoneyCreditedEvent {
    private final String transactionId;
    private final String walletOwnerId;
    private final BigDecimal creditAmount;
}
