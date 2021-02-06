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

public class WalletCommandsTest {
    private static final String customerName = "XXX";

    private FixtureConfiguration<WalletAggregate> fixture;
    private String walletId;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(WalletAggregate.class);
        walletId = "1";
    }

   /* @Test
    public void testSimpleTradeExecution() {
        OrderId sellOrder = new OrderId();
        PortfolioId sellingUser = new PortfolioId();
        TransactionId sellingTransaction = new TransactionId();

        OrderId buyOrder = new OrderId();
        TransactionId buyTransactionId = new TransactionId();
        PortfolioId buyPortfolioId = new PortfolioId();

        CreateSellOrderCommand orderCommand =
                new CreateSellOrderCommand(sellOrder, sellingUser, orderBookId, sellingTransaction, 100, 100);

        TradeExecutedEvent expectedTradeEvent =
                new TradeExecutedEvent(orderBookId, 100, 100, buyOrder, sellOrder, buyTransactionId, sellingTransaction);

        fixture.given(orderBookCreatedEvent,
                new BuyOrderPlacedEvent(orderBookId, buyOrder, buyTransactionId, 200, 100, buyPortfolioId))
                .when(orderCommand)
                .expectEvents(new SellOrderPlacedEvent(orderBookId, sellOrder, sellingTransaction, 100, 100, sellingUser),
                        expectedTradeEvent);
    }*/

    @Test
    public void should_dispatch_createdwallet_event_when_createawallet_command() {
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
    public void should_dispatch_creditedmoney_event() {
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
    public void should_dispatch_debitedmoney_event_when_balance_is_greater_than_debit_amount() {
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
    public void should_not_dispatch_event_when_balance_is_lower_than_debit_amount() {
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
