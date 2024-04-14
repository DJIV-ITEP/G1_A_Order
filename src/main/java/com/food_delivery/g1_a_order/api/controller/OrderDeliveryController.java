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
public class OrderDeliveryController {

    private final OrderService orderService;

    @PutMapping("delivery/accept")
    public ResponseEntity<OrderShowDto> deliveryAcceptOrder(
            @RequestParam("orderId") Long orderId,
            @RequestParam("deliveryId") Long deliveryId) {

        return ResponseEntity.ok(orderService.deliveryChangeOrderStatus(
                orderId,
                deliveryId,
                OrderStatusEnum.ON_THE_WAY.status,
                OrderStatusEnum.READY_TO_PICKUP.status

        ));
    }

    @PutMapping("delivery/delivered")
    public ResponseEntity<OrderShowDto> orderDelivered(
            @RequestParam("orderId") Long orderId,
            @RequestParam("deliveryId") Long deliveryId) {

        return ResponseEntity.ok(orderService.deliveryChangeOrderStatus(
                orderId,
                deliveryId,
                OrderStatusEnum.DELIVERED.status,
                OrderStatusEnum.ON_THE_WAY.status

        ));
    }

    @GetMapping("readyToPickup/delivery/{deliveryId}")
    public ResponseEntity<List<OrderShowDto>> getReadyToPickupOrdersByDelivery(
            @PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity
                .ok(orderService.getOrdersByStatusAndDelivery(deliveryId, OrderStatusEnum.READY_TO_PICKUP.status));
    }

}
