package com.food_delivery.g1_a_order.persistent.enum_;

import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatusEnum {

    CART(OrderStatus.builder().value("Cart").sequence(1).build()),
    PENDING(OrderStatus.builder().value("Pending").sequence(2).build()),
    IN_PEOGRESS(OrderStatus.builder().value("In Progress").sequence(3).build()),
    READY_TO_PICKUP(OrderStatus.builder().value("ready to pickup").sequence(4).build()),
    DELIVERED(OrderStatus.builder().value("Delivered").sequence(5).build());

    public final OrderStatus status;
}
