package com.gaming.wallet.axon.projection;

import com.gaming.wallet.axon.entity.Transaction;
import com.gaming.wallet.axon.entity.TransactionType;
import com.gaming.wallet.axon.event.CreditedMoneyEvent;
import com.gaming.wallet.axon.event.DebitedMoneyEvent;
import com.gaming.wallet.axon.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionProjection {

    private final TransactionRepository repository;

    @EventHandler
    public void on(CreditedMoneyEvent evt) {
        Transaction transaction = new Transaction(
                evt.getTransactionId(), evt.getWalletOwnerId(),
                TransactionType.CREDIT,
                new Timestamp(new Date().getTime()),
                evt.getAmount()
        );
        repository.save(transaction);
    }

    @EventHandler
    public void on(DebitedMoneyEvent evt) {
        Transaction transaction = new Transaction(
                evt.getTransactionId(), evt.getWalletOwnerId(),
                TransactionType.DEBIT,
                new Timestamp(new Date().getTime()),
                evt.getAmount()
        );
        repository.save(transaction);
    }

}
