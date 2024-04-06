package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.payment.PaymentCreateDto;
import com.food_delivery.g1_a_order.api.dto.payment.PaymentShowDto;
import com.food_delivery.g1_a_order.service.OrderPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment-cash-on-delivery")
public class PaymentCashOnDeliveryController {
    private final OrderPaymentService orderPaymentService;
    @PostMapping("/process")
    public ResponseEntity<PaymentShowDto> processPayment(@RequestBody PaymentCreateDto paymentCreateDto) {
        PaymentShowDto payment = orderPaymentService.processPayment(paymentCreateDto);
        return ResponseEntity.ok(payment);
    }
}
