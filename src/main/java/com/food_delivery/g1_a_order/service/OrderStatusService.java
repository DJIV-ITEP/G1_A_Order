package com.food_delivery.g1_a_order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.api.dto.orderStatus.OrderStatusShowDto;
import com.food_delivery.g1_a_order.config.mapper.OrderStatusMapper;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderStatusService {

    private final OrderStatusRepository repository;
    private final OrderStatusMapper mapper;

    public List<OrderStatusShowDto> getOrderStatus() {

        return mapper.toOrderStatusShowDto(repository.findAll());
    }

}
