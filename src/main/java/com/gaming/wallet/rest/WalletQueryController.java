package com.gaming.wallet.rest;

import com.gaming.wallet.entity.Wallet;
import com.gaming.wallet.service.WalletQueryService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/wallets")
@Api(value = "Wallet Queries", description = "Wallet Query Events API")
@AllArgsConstructor
public class WalletQueryController {
    private final WalletQueryService walletQueryService;

    @GetMapping("/{walletOwnerId}")
    public CompletableFuture<Wallet> findById(@PathVariable("walletOwnerId") String walletOwnerId) {
        CompletableFuture<Wallet> byId = this.walletQueryService.findById(walletOwnerId);
        return byId;
    }

    @GetMapping("/{walletOwnerId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "walletOwnerId") String walletOwnerId) {
        return this.walletQueryService.listEventsForAccount(walletOwnerId);
    }


}
