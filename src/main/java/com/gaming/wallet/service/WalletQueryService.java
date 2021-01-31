package com.gaming.wallet.service;

import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletQueryService {
    private final QueryGateway queryGateway;
    private final EventStore eventStore;
    private final AccountRepository repository;



    public Optional<Wallet> findById(String walletOwnerId) {
        return this.repository.findById(walletOwnerId);
        /*return this.queryGateway.query(
                new FindWalletQuery(walletOwnerId),
                ResponseTypes.instanceOf(Wallet.class)
        );*/
    }

    public List<Object> listEventsForAccount(String walletOwnerId) {
        return this.eventStore
                .readEvents(walletOwnerId)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
