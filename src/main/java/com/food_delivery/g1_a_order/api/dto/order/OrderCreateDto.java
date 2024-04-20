package com.food_delivery.g1_a_order.api.dto.order;

import java.util.List;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;

import jakarta.validation.constraints.NotNull;

public record OrderCreateDto(
                Long customerId,
                // Long customerAddressId,
                Long restaurantId,
                // Long restaurantAddressId,
                @NotNull List<OrderItemsCreateDto> orderItems) {

}
