package com.food_delivery.g1_a_order.api.dto.order;

import java.util.List;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;



public record OrderCreateDto(
        Long customerId,
        Long customerAddressId,
        Long restaurantId,
        Long restaurantAddressId,
        List<OrderItemsCreateDto> orderItems) {

}
