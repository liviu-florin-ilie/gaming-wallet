package com.gaming.wallet.service;

import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.query.FindWalletQuery;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletQueryService {
    private final QueryGateway queryGateway;
    private final EventStore eventStore;


    public CompletableFuture<Wallet> findById(String walletOwnerId) {
        return this.queryGateway.query(
                new FindWalletQuery(walletOwnerId), ResponseTypes.instanceOf(Wallet.class));
    }

    public List<Object> listEventsForAccount(String walletOwnerId) {
        return this.eventStore
                .readEvents(walletOwnerId)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
