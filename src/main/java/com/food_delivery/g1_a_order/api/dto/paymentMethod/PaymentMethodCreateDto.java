package com.food_delivery.g1_a_order.api.dto.paymentMethod;

public record PaymentMethodCreateDto(
        String name,
        String route,
        Boolean enabled
)
{
}

