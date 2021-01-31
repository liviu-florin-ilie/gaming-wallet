package com.gaming.wallet.aggregate;

import com.gaming.wallet.command.CreditMoneyCommand;
import com.gaming.wallet.command.DebitMoneyCommand;
import com.gaming.wallet.entity.Transaction;
import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.event.MoneyCreditedEvent;
import com.gaming.wallet.event.MoneyDebitedEvent;
import com.gaming.wallet.event.WalletCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.SortedSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class WalletAggregate {
    @AggregateIdentifier
    private String walletId;
    private BigDecimal balance;
    private String username;

    @AggregateMember
    private SortedSet<Transaction> transactions;

    @EventSourcingHandler
    public void on(WalletCreatedEvent event) {
        this.walletId = event.getId();
        this.username = event.getUsername();
        this.balance = event.getInitialBalance();
    }

    @CommandHandler
    public WalletAggregate(CreditMoneyCommand command) {
        AggregateLifecycle.apply(
                new MoneyCreditedEvent(command.getTransactionId(),
                        command.getWalletOwnerId(),
                        command.getCreditAmount()
                )
        );
    }

    @EventSourcingHandler
    public void on(MoneyCreditedEvent event) {
        this.walletId = event.getWalletOwnerId();

        /*TODO this.wallet.setBalance(this.wallet.getBalance().add(event.getCreditAmount()));

        this.owner = event.getOwner();
        this.balance = event.getInitialBalance();*/
    }

    @CommandHandler
    public void handle(DebitMoneyCommand command) {
        AggregateLifecycle.apply(new MoneyDebitedEvent(command.getTransactionId(),
                command.getWalletOwnerId(),
                command.getAmount()
        ));
    }


}
