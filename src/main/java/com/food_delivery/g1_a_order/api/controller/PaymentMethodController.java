package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.paymentMethod.PaymentMethodCreateDto;
import com.food_delivery.g1_a_order.api.dto.paymentMethod.PaymentMethodShowDto;
import com.food_delivery.g1_a_order.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;
    
//    @PostMapping("/add")
//    public ResponseEntity<PaymentMethodShowDto> createPaymentMethod(@RequestBody PaymentMethodCreateDto paymentMethodCreateDto) {
//        PaymentMethodShowDto paymentMethod = paymentMethodService.createPaymentMethod(paymentMethodCreateDto);
//        return ResponseEntity.ok(paymentMethod);
//    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodShowDto>> getAllPaymentMethods() {
        List<PaymentMethodShowDto> paymentMethods = paymentMethodService.getAllPaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentMethodShowDto> updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethodCreateDto paymentMethodCreateDto) {
        PaymentMethodShowDto paymentMethod = paymentMethodService.updatePaymentMethod(id, paymentMethodCreateDto);
        return ResponseEntity.ok(paymentMethod);
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<PaymentMethodShowDto>> getEnabledPaymentMethods() {
        List<PaymentMethodShowDto> paymentMethods = paymentMethodService.getEnabledPaymentMethods();
        return ResponseEntity.ok(paymentMethods);
    }
}
