package com.food_delivery.g1_a_order.persistent.enum_;

import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING(new OrderStatus("Pending",1)),
    IN_PEOGRESS(new OrderStatus("In Progress",2)),
    DELIVERED(new OrderStatus("Delivered",3));

    public final OrderStatus status;
}
