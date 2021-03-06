package com.gaming.wallet.rest;

import com.gaming.wallet.axon.entity.Wallet;
import com.gaming.wallet.rest.dto.WalletHistoryDTO;
import com.gaming.wallet.service.WalletQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/wallet")
@Api(value = "Wallet Queries", description = "Wallet Query API")
@AllArgsConstructor
public class WalletQueryController {
    private final WalletQueryService walletQueryService;

    @GetMapping("/{walletOwnerId}/balance")
    @ApiOperation("Retrieves the current state of the Wallet Account")
    public CompletableFuture<Wallet> findById(@PathVariable("walletOwnerId") String walletOwnerId) {
        CompletableFuture<Wallet> byId = this.walletQueryService.findById(walletOwnerId);
        return byId;
    }

    @GetMapping("/{walletOwnerId}/events")
    @ApiOperation("Lists all the operations/events on the Wallet Account")
    public CompletableFuture<List<WalletHistoryDTO>> listEventsForAccount(@PathVariable(value = "walletOwnerId") String walletOwnerId) {
        return this.walletQueryService.listEventsForAccount(walletOwnerId);
    }


}
