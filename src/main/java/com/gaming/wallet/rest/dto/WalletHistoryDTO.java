package com.gaming.wallet.rest.dto;

import com.gaming.wallet.axon.event.MoneyEvent;
import lombok.Value;

import java.time.Instant;

@Value
public class WalletHistoryDTO {
    private final String eventType;
    private final Object event;
    private final Instant timestamp;
}
