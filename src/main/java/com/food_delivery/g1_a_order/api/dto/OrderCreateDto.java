package com.food_delivery.g1_a_order.api.dto;

import java.util.List;



public record OrderCreateDto(
        Long customerId,
        Long restaurantId,
        List<OrderItemsCreateDto> orderItems) {

}
