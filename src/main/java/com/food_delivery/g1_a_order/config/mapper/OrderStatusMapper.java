package com.food_delivery.g1_a_order.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.food_delivery.g1_a_order.api.dto.orderStatus.OrderStatusShowDto;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {

        // order status
    OrderStatusShowDto toOrderStatusShowDto(OrderStatus orderStatus);
    List<OrderStatusShowDto> toOrderStatusShowDto(List<OrderStatus> orderStatus);
    
}
