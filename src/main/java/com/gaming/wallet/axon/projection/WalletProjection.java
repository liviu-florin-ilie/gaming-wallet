package com.gaming.wallet.axon.projection;

import com.gaming.wallet.axon.entity.Wallet;
import com.gaming.wallet.axon.event.CreatedWalletEvent;
import com.gaming.wallet.axon.event.CreditedMoneyEvent;
import com.gaming.wallet.axon.event.DebitedMoneyEvent;
import com.gaming.wallet.axon.query.FindWalletQuery;
import com.gaming.wallet.axon.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class WalletProjection {
    private final WalletRepository repository;

    @EventHandler
    public void on(CreatedWalletEvent event) {
        log.debug("Handling a Wallet creation event ", event.getWalletId());
        Wallet wallet = new Wallet(event.getWalletId(), event.getUsername(), event.getInitialBalance());
        repository.save(wallet);
    }

    @EventHandler
    public void on(CreditedMoneyEvent event) {
        String transactionId = event.getTransactionId();
        log.debug("Handling an Wallet Credit command ", transactionId);

        WalletRepository repository = this.repository;
        Optional<Wallet> walletOptional = getWallet(event.getWalletOwnerId(), repository);

        Wallet playerWallet = walletOptional.get();
        playerWallet.setBalance(playerWallet.getBalance().add(event.getAmount()));
        repository.save(playerWallet);
    }

    @EventHandler
    public void on(DebitedMoneyEvent event) {
        String transactionId = event.getTransactionId();
        log.debug("Handling an Wallet Debit command ", transactionId);
        Optional<Wallet> walletOptional = getWallet(event.getWalletOwnerId(), repository);
        Wallet playerWallet = walletOptional.get();
        playerWallet.setBalance(playerWallet.getBalance().subtract(event.getAmount()));
        repository.save(playerWallet);
    }

    @QueryHandler
    public Wallet handle(FindWalletQuery query) {
        log.debug("Handling FindWalletQuery query ", query);
        return this.repository.findById(query.getWalletOwnerId()).orElse(null);
    }

    private Optional<Wallet> getWallet(String walletOwnerId, WalletRepository repository) {
        Assert.notNull(walletOwnerId, "walletOwnerId is null:" + walletOwnerId);
        Optional<Wallet> wallet = repository.findById(walletOwnerId);
        Assert.isTrue(wallet.isPresent(), "The wallet does not exist:" + walletOwnerId);
        return wallet;
    }

}
