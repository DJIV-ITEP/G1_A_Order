package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.paymentStatus.PaymentStatusShowDto;
import com.food_delivery.g1_a_order.config.mapper.PaymentStatusMapper;
import com.food_delivery.g1_a_order.persistent.repository.PaymentStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/paymentStatus")
public class PaymentStatusController {
    @Autowired
    PaymentStatusRepository repository;
    @Autowired
    PaymentStatusMapper paymentStatusMapper;
    @GetMapping()
    public ResponseEntity<List<PaymentStatusShowDto>> getPaymentStatus() {
        var paymentStatus = repository.findAll();
        return ResponseEntity.ok(paymentStatusMapper.toPaymentStatusShowDto(paymentStatus));
    }
}
