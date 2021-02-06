package com.gaming.wallet.event;

import com.gaming.wallet.command.MoneyCommand;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class DebitedMoneyEvent implements MoneyEvent {
    private final String walletOwnerId;
    private final String transactionId;
    private final BigDecimal amount;
}
