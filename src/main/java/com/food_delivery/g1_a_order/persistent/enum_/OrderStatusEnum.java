package com.food_delivery.g1_a_order.persistent.enum_;

import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING(OrderStatus.builder().value("Pending").sequence(1).build()),
    IN_PEOGRESS(OrderStatus.builder().value("In Progress").sequence(2).build()),
    READY_TO_PICKUP(OrderStatus.builder().value("ready to pickup").sequence(3).build()),
    DELIVERED(OrderStatus.builder().value("Delivered").sequence(4).build());


    public final OrderStatus status;
}
