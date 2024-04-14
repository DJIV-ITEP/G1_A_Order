package com.food_delivery.g1_a_order.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.service.OrderItemService;
import com.food_delivery.g1_a_order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderCustomerController {

    private final OrderService orderService;
    private final OrderItemService itemService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("customer/add/item")
    public ResponseEntity<OrderShowDto> addOrderItems(@Valid @RequestBody OrderCreateDto orderCreateDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.addOrderItemToOrder(
                        orderCreateDto.customerId(),
                        orderCreateDto.restaurantId(),
                        orderCreateDto.orderItems()));

    }

    @PutMapping("customer/confirm")
    public ResponseEntity<OrderShowDto> customerConfirmOrder(
            @RequestBody Long orderId,
            @RequestBody Long addressId) {

        return ResponseEntity.ok(orderService.customerChangeOrderStatus(
                orderId,
                addressId,
                OrderStatusEnum.PENDING.status,
                OrderStatusEnum.CART.status));

    }

    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<OrderShowDto>> getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

}
