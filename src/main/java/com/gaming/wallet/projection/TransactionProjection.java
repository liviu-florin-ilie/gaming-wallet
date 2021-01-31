package com.gaming.wallet.projection;

import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.event.MoneyCreditedEvent;
import com.gaming.wallet.event.MoneyDebitedEvent;
import com.gaming.wallet.query.FindWalletQuery;
import com.gaming.wallet.repository.AccountRepository;
import com.gaming.wallet.rest.WalletQueryController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionProjection {
    private final AccountRepository repository;
    private final EventStore eventStore;

    @EventHandler
    public void on(MoneyCreditedEvent event) {
        String transactionId = event.getTransactionId();
        log.debug("Handling an Wallet Credit command {}", transactionId);

        checkTransaction(transactionId);

        AccountRepository repository = this.repository;
        Optional<Wallet> walletOptional = getWallet(event.getWalletOwnerId(), repository);

        Wallet playerWallet = walletOptional.get();
        playerWallet.setBalance(playerWallet.getBalance().add(event.getCreditAmount()));
        repository.save(playerWallet);
    }

    @EventHandler
    public void on(MoneyDebitedEvent event) {
        String transactionId = event.getTransactionId();
        log.debug("Handling an Wallet Debit command {}", transactionId);
        checkTransaction(transactionId);
        Optional<Wallet> walletOptional = getWallet(event.getWalletOwnerId(), repository);
        Wallet playerWallet = walletOptional.get();
        playerWallet.setBalance(playerWallet.getBalance().subtract(event.getDebitAmount()));
        repository.save(playerWallet);
    }

    @QueryHandler
    public Wallet handle(FindWalletQuery query) {
        log.debug("Handling FindWalletQuery query: {}", query);
        return this.repository.findById(query.getWalletOwnerId()).orElse(null);
    }

    private Optional<Wallet> getWallet(String walletOwnerId, AccountRepository repository) {
        Assert.isNull(walletOwnerId, "walletOwnerId is null:" + walletOwnerId);
        Optional<Wallet> wallet = repository.findById(walletOwnerId);
        Assert.isTrue(wallet.isPresent(), "The account does not exist:" + walletOwnerId);
        return wallet;
    }

    private void checkTransaction(String transactionId) {
        boolean empty = eventStore.readEvents(transactionId).asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList()).isEmpty();
        Assert.isTrue(!empty, "The transactionId is already used: " + transactionId);
    }
}
