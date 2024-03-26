package com.food_delivery.g1_a_order.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem toOrderItem(OrderItemsCreateDto orderItemsCreateDto);

    OrderItemsCreateDto toOrderItemsCreateDto(OrderItem orderItem);

    OrderItemShowDto toOrderItemShowDto(OrderItem orderItem);

    List<OrderItemsCreateDto> toOrderItemsCreateDto(List<OrderItem> orderItems);

    List<OrderItem> toOrderItem(List<OrderItemsCreateDto> orderItemsCreateDtos);

    List<OrderItemShowDto> toOrderItemShowDto(List<OrderItem> orderItem);

}
