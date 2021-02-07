package com.gaming.wallet.axon.aggregate;

import com.gaming.wallet.axon.command.CreateWalletCommand;
import com.gaming.wallet.axon.command.CreditMoneyCommand;
import com.gaming.wallet.axon.command.DebitMoneyCommand;
import com.gaming.wallet.axon.event.CreatedWalletEvent;
import com.gaming.wallet.axon.event.CreditedMoneyEvent;
import com.gaming.wallet.axon.event.DebitedMoneyEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class WalletCommandsTest  {
    private static final String customerName = "XXX";

    private FixtureConfiguration<WalletAggregate> fixture;
    private String walletId;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(WalletAggregate.class);
        walletId = "1";
    }

    @Test
    public void shouldDispatchCreatedwalletEventWhenCreatewalletCommand() {
        fixture.givenNoPriorActivity()
                .when(new CreateWalletCommand(
                        walletId,
                        BigDecimal.valueOf(1000),
                        customerName)
                )
                .expectEvents(new CreatedWalletEvent(
                        walletId,
                        BigDecimal.valueOf(1000),
                        customerName)
                );
    }


    @Test
    public void shouldDispatchCreditedmoneyEvent() {
        CreatedWalletEvent createdWalletEvent = new CreatedWalletEvent(
                walletId,
                BigDecimal.valueOf(1000),
                customerName);

        CreditMoneyCommand creditMoneyCommand = new CreditMoneyCommand(
                walletId,
                "1",
                BigDecimal.valueOf(100));
        CreditedMoneyEvent moneyCreditedEvent = new CreditedMoneyEvent(
                "1",
                walletId,
                BigDecimal.valueOf(100));

        fixture.given(createdWalletEvent).when(creditMoneyCommand)
                .expectEvents(moneyCreditedEvent);
    }

    @Test
    public void shouldDispatchDebitedmoneyEventWhenBalanceIsGreaterThanDebitAmount() {
        CreatedWalletEvent createdWalletEvent = new CreatedWalletEvent(
                walletId,
                BigDecimal.valueOf(1000),
                customerName);

        DebitMoneyCommand debitMoneyCommand = new DebitMoneyCommand(
                walletId,
                "1",
                BigDecimal.valueOf(100));
        DebitedMoneyEvent debitedMoneyEvent = new DebitedMoneyEvent(
                walletId,
                "1",
                BigDecimal.valueOf(100));

        fixture.given(createdWalletEvent).when(debitMoneyCommand)
                .expectEvents(debitedMoneyEvent);
    }

    @Test
    public void shouldNotDispatchEventWhenBalanceIsLowerThanDebitAmount() {
        fixture.given(new CreatedWalletEvent(
                walletId,
                BigDecimal.valueOf(1000),
                customerName))
                .when(
                        new DebitMoneyCommand(
                                walletId,
                                "1",
                                BigDecimal.valueOf(5000))
                )
                .expectNoEvents();
    }

}
