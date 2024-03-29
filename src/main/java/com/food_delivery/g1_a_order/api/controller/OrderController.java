package com.food_delivery.g1_a_order.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.OrderItemService;
import com.food_delivery.g1_a_order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
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

    // may be wanted
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add/orderItem")
    public ResponseEntity<String> addOrder(@RequestBody OrderCreateDto orderCreateDto) {

        itemService.addOrderItemToOrder(
                orderCreateDto.customerId(),
                orderCreateDto.restaurantId(),
                orderCreateDto.orderItems());

        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    // @PostMapping("/customer/{customerId}/restaurant/{restaurantId}/orderItem")
    // public ResponseEntity<String> addOrderItemToOrder(@PathVariable("customerId")
    // Long customerId,
    // @PathVariable("restaurantId") Long restaurantId,
    // @RequestBody List<OrderItemsCreateDto> itemDto) {
    // itemService.addOrderItemToOrder(customerId, restaurantId, itemDto);
    // return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    // }

    // todo: change return type
    @PutMapping("{orderId}/change/orderStatus/{orderStatusId}")
    public ResponseEntity<OrderShowDto> changeOrderStatus(@PathVariable("orderId") Long orderId,
            @PathVariable("orderStatusId") Long orderStatusId) {

        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderStatusId));

    }

    @PutMapping("{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable("orderId") Long orderId) {

        if (orderService.confirmOrder(orderId)) {
            return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Failed to confirm order, please check if the order id is correct.");
    }

}
