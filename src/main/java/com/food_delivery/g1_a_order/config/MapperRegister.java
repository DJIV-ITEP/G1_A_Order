package com.food_delivery.g1_a_order.config;

import java.util.List;

import org.mapstruct.Mapper;

import com.food_delivery.g1_a_order.api.dto.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.OrderStatusShowDto;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

@Mapper(componentModel = "spring")
public interface MapperRegister {

    // order status
    OrderStatusShowDto toOrderStatusShowDto(OrderStatus orderStatus);
    List<OrderStatusShowDto> toOrderStatusShowDto(List<OrderStatus> orderStatus);
    
    // order create dto
    Order toOrder(OrderCreateDto orderCreateDto);
    OrderCreateDto toOrderCreateDto(Order order);
    List<OrderCreateDto> toOrderCreateDto(List<Order> order);
    // List<Order> toOrder(List<OrderCreateDto> orderCreateDto);
    
    // order show dto
    Order toOrder(OrderShowDto orderShowDto);
    OrderShowDto toOrderShowDto(Order order);
    List<OrderShowDto> toOrderShowDto(List<Order> order);
    List<Order> toOrder(List<OrderShowDto> orderShowDto);

    
}
