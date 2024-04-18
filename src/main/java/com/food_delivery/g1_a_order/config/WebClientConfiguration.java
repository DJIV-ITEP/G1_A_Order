package com.food_delivery.g1_a_order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${customer.service.url}")
    private String customerServiceBaseUrl;

    @Bean
    public WebClient customerServiceWebClient() {
        return WebClient.builder()
                .baseUrl(customerServiceBaseUrl)
                // Add default headers, timeouts, etc. if needed
                .build();
    }

    // @Bean
    // public WebClient webClient() {
    //     return WebClient.create();
    // }
}
