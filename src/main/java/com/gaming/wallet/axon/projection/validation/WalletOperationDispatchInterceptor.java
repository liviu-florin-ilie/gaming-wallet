package com.gaming.wallet.axon.projection.validation;

import com.gaming.wallet.axon.command.CreditMoneyCommand;
import com.gaming.wallet.axon.command.DebitMoneyCommand;
import com.gaming.wallet.axon.command.MoneyCommand;
import com.gaming.wallet.axon.repository.TransactionRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class WalletOperationDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (i, m) -> {

            if (CreditMoneyCommand.class.equals(m.getPayloadType()) || DebitMoneyCommand.class.equals(m.getPayloadType())) {
                final MoneyCommand moneyCommand = (MoneyCommand) m.getPayload();
                boolean existsById = transactionRepository.existsById(moneyCommand.getTransactionId());
                Assert.isTrue(!existsById, "Transaction already exists: " + moneyCommand.getTransactionId() );
            }
            return m;
        };
    }


}

