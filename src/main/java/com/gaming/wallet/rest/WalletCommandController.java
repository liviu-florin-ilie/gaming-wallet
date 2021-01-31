package com.gaming.wallet.rest;

import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.rest.dto.MoneyAmountDTO;
import com.gaming.wallet.rest.dto.WalletCreationDTO;
import com.gaming.wallet.service.WalletCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping(value = "/wallet")
@Api(value = "Wallet Wallet Commands", description = "Wallet Wallet Commands API")
@AllArgsConstructor
public class WalletCommandController {
    private final WalletCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = CREATED)
    public CompletableFuture<Wallet> createAccount(@RequestBody WalletCreationDTO creationDTO) {
        return this.accountCommandService.createWallet(creationDTO);
    }


    @PutMapping(value = "/credit/{walletOwnerId}/{transactionId}")
    public CompletableFuture<String> creditMoneyToWallet(@PathVariable(value = "walletOwnerId") String walletOwnerId,
                                                         @PathVariable(value = "transactionId") String transactionId,
                                                         @RequestBody MoneyAmountDTO moneyCreditDTO) {
        return this.accountCommandService.creditMoneyToWallet(walletOwnerId, transactionId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{walletOwnerId}/{transactionId}")
    public CompletableFuture<String> debitMoneyFromWallet(@PathVariable(value = "walletOwnerId") String walletOwnerId,
                                                          @PathVariable(value = "transactionId") String transactionId,
                                                          @RequestBody MoneyAmountDTO moneyDebitDTO) {
        return this.accountCommandService.debitMoneyFromWallet(walletOwnerId, transactionId, moneyDebitDTO);
    }


}
