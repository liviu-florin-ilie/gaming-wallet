package com.gaming.wallet.axon.aggregate;

import com.gaming.wallet.axon.command.DebitMoneyCommand;
import com.gaming.wallet.axon.entity.Transaction;
import com.gaming.wallet.axon.event.CreatedWalletEvent;
import com.gaming.wallet.axon.event.DebitedMoneyEvent;
import com.gaming.wallet.axon.projection.validation.WalletOperationDispatchInterceptor;
import com.gaming.wallet.axon.repository.TransactionRepository;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TransactionTest {


    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private FixtureConfiguration<WalletAggregate> fixture;
    private String walletId;
    private WalletOperationDispatchInterceptor walletOperationDispatchInterceptor = new WalletOperationDispatchInterceptor();

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(WalletAggregate.class);
        walletId = "1";
        fixture.registerInjectableResource(new Transaction("1"));
        ReflectionTestUtils.setField(walletOperationDispatchInterceptor, "transactionRepository", transactionRepository);
        fixture.registerInjectableResource(transactionRepository);
        fixture.registerCommandDispatchInterceptor(walletOperationDispatchInterceptor);
    }


    @Test
    public void should_not_dispatch_event_when_transaction_is_already_used() {
        CreatedWalletEvent createdWalletEvent = new CreatedWalletEvent(
                walletId,
                BigDecimal.valueOf(1000),
                "Player1");

        String transactionId = "1";
        DebitMoneyCommand debitMoneyCommandRepeat = new DebitMoneyCommand(
                walletId,
                transactionId,
                BigDecimal.valueOf(100));

        when(transactionRepository.existsById(transactionId)).thenReturn(Boolean.TRUE);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fixture.given(createdWalletEvent).when(debitMoneyCommandRepeat).expectNoEvents();
        });

        String expectedMessage = "Transaction already exists: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void should_dispatch_event_when_transaction_is_fresh() {
        CreatedWalletEvent createdWalletEvent = new CreatedWalletEvent(
                walletId,
                BigDecimal.valueOf(1000),
                "Player1");

        String transactionId = "2";
        DebitMoneyCommand debitMoneyCommandRepeat = new DebitMoneyCommand(
                walletId,
                transactionId,
                BigDecimal.valueOf(100));

        DebitedMoneyEvent debitedMoneyEvent = new DebitedMoneyEvent(
                walletId,
                transactionId,
                BigDecimal.valueOf(100));

        when(transactionRepository.existsById(transactionId)).thenReturn(Boolean.FALSE);
        fixture.given(createdWalletEvent).when(debitMoneyCommandRepeat).expectEvents(debitedMoneyEvent);
    }


}
