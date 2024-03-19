package com.food_delivery.g1_a_order.persistent.enum_;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING("Pending"),
    IN_PEOGRESS("In Progress"),
    DELIVERED("Delivered");

    public final String status;
}
