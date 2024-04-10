package com.food_delivery.g1_a_order.api.dto.orderItem;

import java.time.LocalDateTime;

public record OrderItemShowDto(

        Long id,
        Long itemId,
        Long quantity,
        Double price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt ) {

}
