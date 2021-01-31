package com.gaming.wallet.rest.dto;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class WalletCreationDTO {
    private final BigDecimal initialBalance;
    private final String username;
}
