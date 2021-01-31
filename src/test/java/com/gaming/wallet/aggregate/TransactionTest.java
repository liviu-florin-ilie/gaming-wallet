package com.gaming.wallet.aggregate;

import com.gaming.wallet.command.CreateWalletCommand;
import com.gaming.wallet.command.CreditMoneyCommand;
import com.gaming.wallet.command.DebitMoneyCommand;
import com.gaming.wallet.event.CreatedWalletEvent;
import com.gaming.wallet.event.CreditedMoneyEvent;
import com.gaming.wallet.event.DebitedMoneyEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransactionTest {
    private static final String customerName = "XXX";

    private FixtureConfiguration<WalletAggregate> fixture;
    private String id;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(WalletAggregate.class);
        id = "1";
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
    public void should_dispatch_accountcreated_event_when_createaccount_command() {
        fixture.givenNoPriorActivity()
                .when(new CreateWalletCommand(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName)
                )
                .expectEvents(new CreatedWalletEvent(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName)
                );
    }


    @Test
    public void should_dispatch_moneycredited_event() {
        CreatedWalletEvent createdWalletEvent = new CreatedWalletEvent(
                id,
                BigDecimal.valueOf(1000),
                customerName);

        CreditMoneyCommand creditMoneyCommand = new CreditMoneyCommand(
                id,
                "1",
                BigDecimal.valueOf(100));
        CreditedMoneyEvent moneyCreditedEvent = new CreditedMoneyEvent(
                "1",
                id,
                BigDecimal.valueOf(100));

        fixture.given(createdWalletEvent).when(creditMoneyCommand)
                .expectEvents(moneyCreditedEvent);
    }

    @Test
    public void should_dispatch_moneydebited_event_when_balance_is_greater_than_debit_amount() {
        CreatedWalletEvent createdWalletEvent = new CreatedWalletEvent(
                id,
                BigDecimal.valueOf(1000),
                customerName);

        DebitMoneyCommand debitMoneyCommand = new DebitMoneyCommand(
                id,
                "1",
                BigDecimal.valueOf(100));
        DebitedMoneyEvent debitedMoneyEvent = new DebitedMoneyEvent(
                id,
                "1",
                BigDecimal.valueOf(100));

        fixture.given(createdWalletEvent).when(debitMoneyCommand)
                .expectEvents(debitedMoneyEvent);
    }


    @Test
    public void should_not_dispatch_event_when_balance_is_lower_than_debit_amount() {
        fixture.given(new CreatedWalletEvent(
                id,
                BigDecimal.valueOf(1000),
                customerName))
                .when(
                        new DebitMoneyCommand(
                                id,
                                "1",
                                BigDecimal.valueOf(5000))
                )
                .expectNoEvents();
    }
}
