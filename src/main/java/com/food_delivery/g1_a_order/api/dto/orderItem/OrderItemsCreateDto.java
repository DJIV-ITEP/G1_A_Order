package com.food_delivery.g1_a_order.api.dto.orderItem;

import org.springframework.validation.annotation.Validated;

@Validated
public record OrderItemsCreateDto(
        Long itemId,
        Long quantity,
        Double price
        ) {

}
