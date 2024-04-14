package com.food_delivery.g1_a_order.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderRestaurantController {

    private final OrderService orderService;

    @PutMapping("{orderId}/restaurant/{restaurantId}/startPreparing")
    public ResponseEntity<OrderShowDto> restaurantStartPreparingOrder(
            @PathVariable("orderId") Long orderId,
            @PathVariable("restaurantId") Long restaurantId) {

        return ResponseEntity.ok(orderService.restaurantChangeOrderStatus(
                orderId,
                restaurantId,
                OrderStatusEnum.IN_PEOGRESS.status,
                OrderStatusEnum.PENDING.status));
    }

    @PutMapping("{orderId}/restaurant/{restaurantId}/readyToPickup")
    public ResponseEntity<OrderShowDto> restaurantCompleteOrder(
            @PathVariable("orderId") Long orderId,
            @PathVariable("restaurantId") Long restaurantId) {

        return ResponseEntity.ok(orderService.restaurantChangeOrderStatus(
                orderId,
                restaurantId,
                OrderStatusEnum.READY_TO_PICKUP.status,
                OrderStatusEnum.IN_PEOGRESS.status));
    }

    @PutMapping("{orderId}/restaurant/{restaurantId}/cancel")
    public ResponseEntity<OrderShowDto> restaurantCancel(
            @PathVariable("orderId") Long orderId,
            @PathVariable("restaurantId") Long restaurantId) {

        return ResponseEntity.ok(orderService.restaurantChangeOrderStatus(
                orderId,
                restaurantId,
                OrderStatusEnum.CANCELED.status,
                OrderStatusEnum.IN_PEOGRESS.status));
    }

    @PutMapping("{orderId}/restaurant/{restaurantId}/reject")
    public ResponseEntity<OrderShowDto> restaurantReject(
            @PathVariable("orderId") Long orderId,
            @PathVariable("restaurantId") Long restaurantId) {

        return ResponseEntity.ok(orderService.restaurantChangeOrderStatus(
                orderId,
                restaurantId,
                OrderStatusEnum.REJECT.status,
                OrderStatusEnum.PENDING.status));
    }

    @GetMapping("pending/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderShowDto>> getPendingOrdersByRestaurant(
            @PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity
                .ok(orderService.getOrdersByStatusAndRestaurant(restaurantId, OrderStatusEnum.PENDING.status));
    }

    @GetMapping("inProgress/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderShowDto>> getInProgressOrdersByRestaurant(
            @PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity
                .ok(orderService.getOrdersByStatusAndRestaurant(restaurantId, OrderStatusEnum.IN_PEOGRESS.status));
    }

}
