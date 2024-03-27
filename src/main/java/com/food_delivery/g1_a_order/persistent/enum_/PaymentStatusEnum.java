package com.food_delivery.g1_a_order.persistent.enum_;

import com.food_delivery.g1_a_order.persistent.entity.PaymentStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatusEnum {

    PENDING(PaymentStatus.builder().value("Pending").sequence(0).build()),
    PAID(PaymentStatus.builder().value("Paid ").sequence(1).build()),
    REFUND(PaymentStatus.builder().value("Refund").sequence(2).build()),
    FAILED(PaymentStatus.builder().value("Failed").sequence(3).build());

    public final PaymentStatus status;
}
