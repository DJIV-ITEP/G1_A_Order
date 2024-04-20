package com.food_delivery.g1_a_order.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.food_delivery.g1_a_order.config.MailProperties;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmailService {

        @Autowired
        @Qualifier("mailServiceWebClient")
        private WebClient webClient;
        private final MailProperties mailProperties;

        public Mono<String> sendSimpleMessage(String toEmail, String subject, String body) {

                String auth = "api:" + mailProperties.getToken();
                String encodedAuth = new String(Base64.getEncoder().encode(auth.getBytes()));

                return webClient.post()
                                .uri("https://api.mailgun.net/v3/" + mailProperties.getDomain() + "/messages")
                                .header("Authorization",
                                                "Basic " + encodedAuth)
                                .body(BodyInserters
                                                .fromFormData("from",
                                                                "Wasale System <" + mailProperties.getSender() + ">")
                                                .with("to", toEmail)
                                                .with("subject", subject)
                                                .with("text", body))
                                .retrieve()
                                .bodyToMono(String.class);
        }

}
