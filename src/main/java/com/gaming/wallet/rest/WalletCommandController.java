package com.gaming.wallet.rest;

import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.rest.dto.MoneyAmountDTO;
import com.gaming.wallet.rest.dto.WalletCreationDTO;
import com.gaming.wallet.service.WalletCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping(value = "/wallet")
@Api(value = "Wallet Account Commands", description = "This is the controller that handles the \"Command\" part of the API")
@AllArgsConstructor
public class WalletCommandController {
    private final WalletCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = CREATED)
    @ApiOperation(value = "Creates a new Wallet Account")
    public CompletableFuture<Wallet> createAccount(@RequestBody WalletCreationDTO creationDTO) {
        return this.accountCommandService.createWallet(creationDTO);
    }


    @PutMapping(value = "/credit/{walletOwnerId}/{transactionId}")
    @ApiOperation(value = "Adds the specified amount into  the Wallet Account")
    public CompletableFuture<String> creditMoneyToWallet(@PathVariable(value = "walletOwnerId") String walletOwnerId,
                                                         @PathVariable(value = "transactionId") String transactionId,
                                                         @RequestBody MoneyAmountDTO moneyCreditDTO) {
        return this.accountCommandService.creditMoneyToWallet(walletOwnerId, transactionId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{walletOwnerId}/{transactionId}")
    @ApiOperation(value="Pulls out money from the Wallet Account")
    public CompletableFuture<String> debitMoneyFromWallet(@PathVariable(value = "walletOwnerId") String walletOwnerId,
                                                          @PathVariable(value = "transactionId") String transactionId,
                                                          @RequestBody MoneyAmountDTO moneyDebitDTO) {
        return this.accountCommandService.debitMoneyFromWallet(walletOwnerId, transactionId, moneyDebitDTO);
    }


}
