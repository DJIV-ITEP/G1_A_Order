package com.food_delivery.g1_a_order.api.dto;

import java.time.LocalDateTime;

public record OrderItemShowDto(

        Long id,
        Long itemId,
        Long quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
