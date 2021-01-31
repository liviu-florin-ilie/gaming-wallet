package com.gaming.wallet.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindWalletQuery {
    private String walletOwnerId;
}
