package com.gaming.wallet.axon.aggregate;

import com.gaming.wallet.axon.command.CreateWalletCommand;
import com.gaming.wallet.axon.command.CreditMoneyCommand;
import com.gaming.wallet.axon.command.DebitMoneyCommand;
import com.gaming.wallet.axon.event.CreatedWalletEvent;
import com.gaming.wallet.axon.event.CreditedMoneyEvent;
import com.gaming.wallet.axon.event.DebitedMoneyEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class WalletAggregate {
    @AggregateIdentifier
    private String walletId;
    private BigDecimal balance;
    private String username;

    @CommandHandler
    public WalletAggregate(CreateWalletCommand command) {
        CreatedWalletEvent createdWalletEvent = new CreatedWalletEvent(command.getWalletId(), command.getInitialBalance(), command.getUsername());
        AggregateLifecycle.apply(createdWalletEvent);
    }

    @EventSourcingHandler
    public void on(CreatedWalletEvent event) {
        this.walletId = event.getWalletId();
        this.username = event.getUsername();
        this.balance = event.getInitialBalance();
    }

    @CommandHandler
    public void handle(CreditMoneyCommand command) {
        CreditedMoneyEvent creditedMoneyEvent = new CreditedMoneyEvent(command.getTransactionId(),
                command.getWalletOwnerId(),
                command.getAmount()
        );
        AggregateLifecycle.apply(creditedMoneyEvent);
    }


    @EventSourcingHandler
    public void on(CreditedMoneyEvent event) {
        this.balance = this.balance.add(event.getAmount());
    }

    @CommandHandler
    public void handle(DebitMoneyCommand command) {
        AggregateLifecycle.apply(new DebitedMoneyEvent(
                command.getWalletOwnerId(),
                command.getTransactionId(),
                command.getAmount()
        ));
    }


    @EventSourcingHandler
    public void on(DebitedMoneyEvent event) {
        Assert.isTrue(this.balance.compareTo(event.getAmount()) > 0, "Not enough funds");
        this.balance = this.balance.subtract(event.getAmount());
    }


}
