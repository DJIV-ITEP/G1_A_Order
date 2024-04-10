package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.payment.PaymentCreateDto;
import com.food_delivery.g1_a_order.service.OrderPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment-cash-on-delivery")
public class PaymentCashOnDeliveryController {
    private final OrderPaymentService orderPaymentService;

    @PostMapping("/process")
    public ResponseEntity<OrderShowDto> processPayment(@RequestBody PaymentCreateDto paymentCreateDto) {
        OrderShowDto order = orderPaymentService.processCashPayment(paymentCreateDto);
        return ResponseEntity.ok(order);
    }
}
