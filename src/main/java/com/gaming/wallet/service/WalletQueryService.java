package com.gaming.wallet.service;

import com.gaming.wallet.axon.entity.Wallet;
import com.gaming.wallet.axon.query.FindWalletQuery;
import com.gaming.wallet.rest.dto.WalletHistoryDTO;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
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

    public CompletableFuture<List<WalletHistoryDTO>> listEventsForAccount(String walletOwnerId) {
        CompletableFuture<List<WalletHistoryDTO>> completableFuture = CompletableFuture.supplyAsync(() ->
                eventStore.readEvents(walletOwnerId).asStream().map(this::domainEventToAggregateHistory).collect(Collectors.toList())
        );
        return completableFuture;
    }


    private WalletHistoryDTO domainEventToAggregateHistory(DomainEventMessage<?> event) {
        return new WalletHistoryDTO(event.getPayloadType().getSimpleName(), event.getPayload(), event.getTimestamp());
    }
}
