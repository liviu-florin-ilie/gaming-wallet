package com.gaming.wallet.aggregate;

import com.gaming.wallet.command.CreditMoneyCommand;
import com.gaming.wallet.event.MoneyCreditedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionTest {
    private static final String customerName = "XXX";

    private FixtureConfiguration<WalletAggregate> fixture;
    private String id;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(WalletAggregate.class);
        id = UUID.randomUUID().toString();
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
    public void should_dispatch_moneycredited_event_when_balance_is_lower_than_debit_amount() {

        CreditMoneyCommand creditMoneyCommand = new CreditMoneyCommand(
                id,
                customerName,
                BigDecimal.valueOf(100));
        MoneyCreditedEvent moneyCreditedEvent = new MoneyCreditedEvent(
                id,
                customerName,
                BigDecimal.valueOf(100));

        fixture.givenNoPriorActivity().when(creditMoneyCommand)
                .expectEvents(moneyCreditedEvent);
    }
}
