package com.food_delivery.g1_a_order.api.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrResponse {
    private String timestamp;
    private String path;
    private int status;
    private String error;
    private String message;
}
