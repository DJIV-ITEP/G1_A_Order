package com.food_delivery.g1_a_order.persistent.enum_;

import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethodEnum {
    COD(PaymentMethod.builder().name("cash on delivery").route("COD").enabled(true).build());
    public final PaymentMethod paymentMethod;
}
