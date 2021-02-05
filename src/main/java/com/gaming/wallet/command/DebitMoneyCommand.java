package com.gaming.wallet.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitMoneyCommand implements MoneyCommand{
    @TargetAggregateIdentifier
    private String walletOwnerId;
    private String transactionId;
    private BigDecimal amount;
}
