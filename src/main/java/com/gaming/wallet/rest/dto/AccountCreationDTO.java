package com.gaming.wallet.rest.dto;

import lombok.Value;

import java.math.BigDecimal;
@Value
public class AccountCreationDTO {
    private final String name;
    private final BigDecimal initialBalance;
}
