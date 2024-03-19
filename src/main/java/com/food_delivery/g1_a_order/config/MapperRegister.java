package com.food_delivery.g1_a_order.config;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.food_delivery.g1_a_order.api.dto.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.OrderStatusShowDto;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

@Mapper(componentModel = "spring")
public interface MapperRegister {

    // order status
    OrderStatusShowDto toOrderStatusShowDto(OrderStatus orderStatus);
    List<OrderStatusShowDto> toOrderStatusShowDto(List<OrderStatus> orderStatus);
    
    // order
    Order toOrder(OrderCreateDto orderCreateDto);
    OrderCreateDto toOrderCreateDto(Order order);
    List<OrderCreateDto> toOrderCreateDto(List<Order> order);
    List<Order> toOrder(List<OrderCreateDto> orderCreateDto);
    
}
