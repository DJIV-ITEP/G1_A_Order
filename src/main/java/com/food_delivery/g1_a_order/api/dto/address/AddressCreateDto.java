package com.food_delivery.g1_a_order.api.dto.address;

public record AddressCreateDto(
        Long customerId,
        Double latitude,
        Double longitude,
        String description
) {

}
