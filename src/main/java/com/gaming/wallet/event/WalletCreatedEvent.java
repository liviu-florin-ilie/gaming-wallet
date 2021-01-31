package com.gaming.wallet.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletCreatedEvent {
    private final String id;
    private final BigDecimal initialBalance;
    private final String username;
}
