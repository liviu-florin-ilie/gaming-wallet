package com.gaming.wallet.projection.validation;

import com.gaming.wallet.command.CreditMoneyCommand;
import com.gaming.wallet.command.DebitMoneyCommand;
import com.gaming.wallet.command.MoneyCommand;
import com.gaming.wallet.repository.TransactionRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                if (transactionRepository.existsById(moneyCommand.getTransactionId())) {
                    throw new IllegalStateException(String.format("Transaction already exists: ", moneyCommand.getTransactionId()
                    ));
                }
            }
            return m;
        };
    }


}

