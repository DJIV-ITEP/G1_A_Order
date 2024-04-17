package com.food_delivery.g1_a_order.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final WebClient webClient;

    @Value("${mail.token}")
    private String mailToken;

    @Value("${mail.domain}")
    private String mailDomain;

    @Value("${mail.sender}")
    private String mailSender;

    public Mono<String> sendSimpleMessage(String toEmail, String subject, String body) {

        String auth = "api:" + mailToken;
        String encodedAuth = new String(Base64.getEncoder().encode(auth.getBytes()));

        return webClient.post()
                .uri("https://api.mailgun.net/v3/" + mailDomain + "/messages")
                .header("Authorization",
                        "Basic " + encodedAuth)
                .body(BodyInserters.fromFormData("from", "Wasale System <" + mailSender + ">")
                        .with("to", toEmail)
                        .with("subject", subject)
                        .with("text", body))
                .retrieve()
                .bodyToMono(String.class);
    }

}
