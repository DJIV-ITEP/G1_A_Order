package com.food_delivery.g1_a_order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    @Qualifier("customerServiceWebClient")
    private  WebClient webClient;

    // todo: handle fetching email from customer service
    public String getEmail(Long customerId) {

        return "";
    }

}
