package com.gaming.wallet.axon.event;

import lombok.Data;

import java.math.BigDecimal;

 @Data
public class CreatedWalletEvent {
    private final String walletId;
    private final BigDecimal initialBalance;
    private final String username;
}
