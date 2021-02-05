package com.gaming.wallet.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gaming.wallet.projection.validation.*;

@Configuration
public class AxonConfig {

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus,
                                         WalletOperationDispatchInterceptor accountCreationDispatchInterceptor) {
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .dispatchInterceptors(accountCreationDispatchInterceptor)
                .build();
    }
}