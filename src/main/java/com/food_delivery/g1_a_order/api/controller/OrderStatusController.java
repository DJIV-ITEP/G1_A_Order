package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.orderStatus.OrderStatusShowDto;
import com.food_delivery.g1_a_order.config.mapper.OrderStatusMapper;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orderStatus")
public class OrderStatusController {

    OrderStatusRepository repository;
    @Autowired
    OrderStatusMapper  orderStatusMapper;

    @GetMapping()
    public ResponseEntity<List<OrderStatusShowDto>> getOrderStatus() {

        var orderStatus = repository.findAll();
        return ResponseEntity.ok(orderStatusMapper.toOrderStatusShowDto(orderStatus));
    }



}
