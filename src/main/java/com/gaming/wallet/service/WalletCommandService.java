package com.gaming.wallet.service;

import com.gaming.wallet.command.CreateWalletCommand;
import com.gaming.wallet.command.CreditMoneyCommand;
import com.gaming.wallet.command.DebitMoneyCommand;
import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.rest.dto.MoneyAmountDTO;
import com.gaming.wallet.rest.dto.WalletCreationDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class WalletCommandService {
    private final CommandGateway commandGateway;


    public CompletableFuture<String> creditMoneyToWallet(String walletOwnerId, String transactionId, MoneyAmountDTO moneyCreditDTO) {
        CreditMoneyCommand creditMoneyCommand = new CreditMoneyCommand(walletOwnerId, transactionId,  moneyCreditDTO.getAmount());
        return this.commandGateway.send(creditMoneyCommand);
    }

    public CompletableFuture<String> debitMoneyFromWallet(String walletOwnerId, String transactionId, MoneyAmountDTO moneyDebitDTO) {
        DebitMoneyCommand debitMoneyCommand = new DebitMoneyCommand(walletOwnerId, transactionId, moneyDebitDTO.getAmount());
        return this.commandGateway.send(debitMoneyCommand);
    }

    public CompletableFuture<Wallet> createWallet(WalletCreationDTO creationDTO) {
        CreateWalletCommand createWalletCommand = new CreateWalletCommand(
                UUID.randomUUID().toString(),
                creationDTO.getInitialBalance(),
                creationDTO.getUsername()
        );

        return this.commandGateway.send(createWalletCommand);
    }
}
