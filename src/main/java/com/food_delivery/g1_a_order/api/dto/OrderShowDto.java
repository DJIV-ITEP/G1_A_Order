package com.food_delivery.g1_a_order.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderShowDto(
        Long id,
        Long customerId,
        Long restaurantId,
        Long deliveryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        OrderStatusShowDto orderStatus,

        List<OrderItemShowDto> orderItems) {

}
