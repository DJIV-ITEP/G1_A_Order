package com.food_delivery.g1_a_order.api.dto.order;

import java.util.List;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record OrderCreateDto(
                Long customerId,
                // Long customerAddressId,
                Long restaurantId,
                // Long restaurantAddressId,
                @Valid @NotNull List<OrderItemsCreateDto> orderItems) {

}
