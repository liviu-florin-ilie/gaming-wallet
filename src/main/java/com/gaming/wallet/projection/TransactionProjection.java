package com.gaming.wallet.projection;

import com.gaming.wallet.entity.Transaction;
import com.gaming.wallet.entity.TransactionType;
import com.gaming.wallet.event.CreditedMoneyEvent;
import com.gaming.wallet.event.DebitedMoneyEvent;
import com.gaming.wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionProjection {

    private final TransactionRepository repository;
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    @EventHandler
    public void on(CreditedMoneyEvent evt) {
        Transaction transaction = new Transaction(
                evt.getTransactionId(), evt.getWalletOwnerId(),
                TransactionType.CREDIT,
                new Timestamp(new Date().getTime()),
                evt.getAmount()
        );
        Assert.isTrue(repository.existsById(transaction.getId()), "The transactionid has already been used"+ transaction.getId() );
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
        Assert.isTrue(repository.existsById(transaction.getId()), "The transactionid has already been used"+ transaction.getId() );
        repository.save(transaction);
    }


    /*private void checkTransaction(String transactionId) {
        boolean empty = eventStore.readEvents(transactionId).asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList()).isEmpty();
        Assert.isTrue(!empty, "The transactionId is already used: " + transactionId);
    }*/

   /* @EventHandler
    public void on(RedeemedEvt evt) {
        transactions.stream()
                .filter(cs -> evt.getId().equals(cs.getId()))
                .findFirst()
                .ifPresent(cardSummary -> {
                    CardSummary updatedCardSummary = cardSummary.deductAmount(evt.getAmount());
                    transactions.remove(cardSummary);
                    transactions.add(updatedCardSummary);
                });
    }

    @QueryHandler // (2)
    public List<CardSummary> fetch(FetchCardSummariesQuery query) {
        return transactions.stream()
                .skip(query.getOffset())
                .limit(query.getSize())
                .collect(Collectors.toList());
    }*/
}
