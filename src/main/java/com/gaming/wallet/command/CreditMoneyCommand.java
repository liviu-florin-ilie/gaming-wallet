package com.gaming.wallet.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditMoneyCommand {
    @TargetAggregateIdentifier
    private String transactionId;
    private String walletOwnerId;
    private BigDecimal creditAmount;

}
