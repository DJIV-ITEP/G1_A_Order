package com.food_delivery.g1_a_order.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.OrderStatusShowDto;
import com.food_delivery.g1_a_order.config.MapperRegister;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orderStatus")
public class OrderStatusController {

    OrderStatusRepository repository;
    @Autowired
    MapperRegister mapper;

    @GetMapping()
    List<OrderStatusShowDto> getOrderStatus() {

        var orderStatus = repository.findAll();
        return mapper.toOrderStatusShowDto(orderStatus);
    }



}
