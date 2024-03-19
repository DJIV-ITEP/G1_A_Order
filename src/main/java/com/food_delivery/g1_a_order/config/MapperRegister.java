package com.food_delivery.g1_a_order.config;

import java.util.List;

import org.mapstruct.Mapper;

import com.food_delivery.g1_a_order.api.dto.OrderStatusShowDto;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

@Mapper(componentModel = "spring")
public interface MapperRegister {

    OrderStatusShowDto toOrderStatusShowDto(OrderStatus orderStatus);
    List<OrderStatusShowDto> toOrderStatusShowDto(List<OrderStatus> orderStatus);
    
}
