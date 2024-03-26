package com.food_delivery.g1_a_order.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.OrderItemService;
import com.food_delivery.g1_a_order.service.OrderService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService itemService;

    @GetMapping
    public ResponseEntity<List<OrderShowDto>> getOrders() {

        return ResponseEntity.ok(orderService.getOrders());

    }

    @PostMapping("add")
    public ResponseEntity<String> addOrder(@RequestBody OrderCreateDto OrderCreateDto) {

        if (orderService.createOrder(OrderCreateDto)) {
            return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
        }

        return ResponseEntity.ok(ResponseMsg.NOT_FOUND.message);
    }

    @PostMapping("{orderId}/add/orderItem")
    public ResponseEntity<String> addOrderItemToOrder(@PathVariable("orderId") Long orderId,
            @RequestBody OrderItemsCreateDto itemDto) {

        itemService.addOrderItemToOrder(orderId, itemDto);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    @PutMapping("{orderId}/change/orderStatus/{orderStatusId}")
    public OrderShowDto changeOrderStatus(@PathVariable("orderId") Long orderId,
            @PathVariable("orderStatusId") Long orderStatusId) {

        return orderService.changeOrderStatus(orderId, orderStatusId);
    }

}
