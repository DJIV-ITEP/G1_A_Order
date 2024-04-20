package com.food_delivery.g1_a_order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestaurantWebClientConfiguration {

    @Value("${restaurant.service.url}")
    private String restaurantServiceBaseUrl;

    @Primary
    @Bean
    public WebClient restaurantServiceWebClient() {
        return WebClient.builder()
                .baseUrl(restaurantServiceBaseUrl)
                // Add default headers, timeouts, etc. if needed
                .build();
    }
}
