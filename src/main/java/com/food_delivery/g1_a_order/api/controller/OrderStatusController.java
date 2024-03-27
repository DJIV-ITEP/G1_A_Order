package com.food_delivery.g1_a_order.api.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.InjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.orderStatus.OrderStatusShowDto;
import com.food_delivery.g1_a_order.config.mapper.OrderStatusMapper;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orderStatus")
public class OrderStatusController {
    OrderStatusRepository repository;

    OrderStatusMapper  orderStatusMapper;

    @GetMapping()
    List<OrderStatusShowDto> getOrderStatus() {

        var orderStatus = repository.findAll();
        return orderStatusMapper.toOrderStatusShowDto(orderStatus);
    }



}
