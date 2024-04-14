package com.food_delivery.g1_a_order.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.persistent.entity.Order;

@Mapper(componentModel = "spring" ,imports = {Order.class})
public interface OrderMapper {

        // order create dto
    Order toOrder(OrderCreateDto orderCreateDto);
    OrderCreateDto toOrderCreateDto(Order order);
    List<OrderCreateDto> toOrderCreateDto(List<Order> order);
    // List<Order> toOrder(List<OrderCreateDto> orderCreateDto);
    
    // order show dto

    Order toOrder(OrderShowDto orderShowDto);
    // @Mapping(target = "totalPrice", expression = "java(order.getTotalPrice())")
    OrderShowDto toOrderShowDto(Order order);
    List<OrderShowDto> toOrderShowDto(List<Order> order);
    List<Order> toOrder(List<OrderShowDto> orderShowDto);


}
