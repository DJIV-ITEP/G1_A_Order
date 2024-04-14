package com.food_delivery.g1_a_order.api.dto.order;

import com.food_delivery.g1_a_order.api.dto.address.AddressShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.api.dto.orderStatus.OrderStatusShowDto;
import com.food_delivery.g1_a_order.api.dto.payment.PaymentShowDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderShowDto(
                Long id,
                Long customerId,
                Long restaurantId,
                Long restaurantAddressId,
                Long deliveryId,
                float totalPrice,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                OrderStatusShowDto orderStatus,
                AddressShowDto address,
                PaymentShowDto payment,
                List<OrderItemShowDto> orderItems) {

}
