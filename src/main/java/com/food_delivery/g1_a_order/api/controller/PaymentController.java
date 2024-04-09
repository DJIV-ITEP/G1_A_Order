package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.payment.PaymentShowDto;
import com.food_delivery.g1_a_order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping()
    public ResponseEntity<List<PaymentShowDto>> getAll() {
        List<PaymentShowDto> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }
    @GetMapping("{id}")
    public ResponseEntity<PaymentShowDto> getPayment(@PathVariable("id") Long id) {
        PaymentShowDto payment = paymentService.getPayment(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<PaymentShowDto>> getPaymentsByStatus(@PathVariable("statusId") Long statusId) {
        List<PaymentShowDto> payments = paymentService.getPaymentsByStatus(statusId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentShowDto>> getPaymentsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<PaymentShowDto> payments = paymentService.getPaymentsByCustomerId(customerId);
        return ResponseEntity.ok(payments);
    }


}
