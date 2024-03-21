package com.food_delivery.g1_a_order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.api.dto.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.OrderShowDto;
import com.food_delivery.g1_a_order.config.MapperRegister;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    MapperRegister mapper;

    private final OrderRepository orderRepository;

    public List<OrderShowDto> getOrders() {

        return mapper.toOrderShowDto(orderRepository.findAll());
    }

    public boolean createOrder(OrderCreateDto OrderCreateDto) {
        
        Order order = mapper.toOrder(OrderCreateDto);
        order.setOrderStatus(OrderStatusEnum.PENDING.status);
        return orderRepository.save(order) == order;
    }

}
