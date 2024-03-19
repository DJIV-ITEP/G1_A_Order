package com.food_delivery.g1_a_order.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.OrderCreateDto;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.service.OrderService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@AllArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService OrderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {

        return ResponseEntity.ok(OrderService.getOrders());
    }

    @PostMapping("add")
    public ResponseEntity<String> addOrder(@RequestBody OrderCreateDto OrderCreateDto) {

        if(OrderService.createOrder(OrderCreateDto));
            return ResponseEntity.ok("Seccessfully added order");
            
    }
    
    

}
