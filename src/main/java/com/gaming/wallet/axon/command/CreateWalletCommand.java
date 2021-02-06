package com.gaming.wallet.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateWalletCommand {
    @TargetAggregateIdentifier
    private String walletId;
    private BigDecimal initialBalance;
    private String username;
}
