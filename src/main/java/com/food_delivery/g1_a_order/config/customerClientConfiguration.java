package com.food_delivery.g1_a_order.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationProperties(prefix = "customer")
public class customerClientConfiguration {

  private String url;

    @Bean
    @Qualifier("customerServiceWebClient")
    public WebClient customerServiceWebClient() {
        return WebClient.builder()
                .baseUrl(url)
                // Add default headers, timeouts, etc. if needed
                .build();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
