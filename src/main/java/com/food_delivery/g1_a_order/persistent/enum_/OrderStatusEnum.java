package com.food_delivery.g1_a_order.persistent.enum_;

import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING(new OrderStatus("Pending")),
    IN_PEOGRESS(new OrderStatus("In Progress")),
    DELIVERED(new OrderStatus("Delivered"));

    public final OrderStatus status;
}
