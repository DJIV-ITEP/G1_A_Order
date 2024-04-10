package com.food_delivery.g1_a_order.api.dto.payment;

import com.food_delivery.g1_a_order.api.dto.paymentMethod.PaymentMethodShowDto;
import com.food_delivery.g1_a_order.api.dto.paymentStatus.PaymentStatusShowDto;

public record PaymentShowDto(
        Long id,
        Double amount,
        String description,
        PaymentStatusShowDto paymentStatus,
        PaymentMethodShowDto paymentMethod
) {
}
