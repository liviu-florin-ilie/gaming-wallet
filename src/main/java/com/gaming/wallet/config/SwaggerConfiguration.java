package com.gaming.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    final String BASE_PACKAGE = "com.gaming.wallet";

    @Bean
    public Docket apiDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Gaming wallet demo app with CQRS and AXON",
                "App demonstrates CQRS & ES based on Spring Boot and Axon while doing basic operations on a wallet",
                "0.0.1-SNAPSHOT",
                "Terms of Service",
                new Contact("Liviu Florin ILIE",
                         "https://www.linkedin.com/in/liviu-florin-i-a094367/",
                        "ilie.liviu@gmail.com"),
                "",
                "",
                Collections.emptyList());
    }
}
