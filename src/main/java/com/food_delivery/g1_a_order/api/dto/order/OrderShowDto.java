package com.food_delivery.g1_a_order.api.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.api.dto.orderStatus.OrderStatusShowDto;

public record OrderShowDto(
        Long id,
        Long customerId,
        Long customerAddressId,
        Long restaurantId,
        Long restaurantAddressId,
        Long deliveryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        OrderStatusShowDto orderStatus,

        List<OrderItemShowDto> orderItems) {

}
