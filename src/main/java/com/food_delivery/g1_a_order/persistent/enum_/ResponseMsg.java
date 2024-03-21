package com.food_delivery.g1_a_order.persistent.enum_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResponseMsg {

    SUCCESS("Success"),
    NOT_FOUND("Not_Found");

    public final String message;


}
