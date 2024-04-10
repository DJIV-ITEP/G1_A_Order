package com.food_delivery.g1_a_order.api.dto.paymentMethod;

public record PaymentMethodShowDto(
        Long id,
        String name,
        String route,
        boolean enabled
)
{
}

