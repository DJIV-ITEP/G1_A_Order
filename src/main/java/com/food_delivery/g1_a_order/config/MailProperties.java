package com.food_delivery.g1_a_order.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    
    private String token;
    private String domain;
    private String sender;

    public String getToken() {
        return token;
    }
    public String getDomain() {
        return domain;
    }
    public String getSender() {
        return sender;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    @Bean
    @Qualifier("mailServiceWebClient")
    public WebClient mailServiceWebClient() {
        return WebClient.create();
    }

    


}
