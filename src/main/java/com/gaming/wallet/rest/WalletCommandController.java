package com.gaming.wallet.rest;

import com.gaming.wallet.axon.entity.Wallet;
import com.gaming.wallet.rest.dto.MoneyTransactionDTO;
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


    @PutMapping(value = "/credit/{walletOwnerId}")
    @ApiOperation(value = "Adds the specified amount into the Wallet Account")
    public CompletableFuture<String> creditMoneyToWallet(@PathVariable(value = "walletOwnerId") String walletOwnerId,
                                                         @RequestBody MoneyTransactionDTO moneyCreditDTO) {
        return this.accountCommandService.creditMoneyToWallet(walletOwnerId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{walletOwnerId}")
    @ApiOperation(value="Pulls out the specified amount from the Wallet Account")
    public CompletableFuture<String> debitMoneyFromWallet(@PathVariable(value = "walletOwnerId") String walletOwnerId,
                                                          @RequestBody MoneyTransactionDTO moneyDebitDTO) {
        return this.accountCommandService.debitMoneyFromWallet(walletOwnerId, moneyDebitDTO);
    }


}
