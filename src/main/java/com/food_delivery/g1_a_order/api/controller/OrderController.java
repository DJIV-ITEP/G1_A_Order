package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.OrderItemService;
import com.food_delivery.g1_a_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
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
    public ResponseEntity<OrderShowDto> addOrderItems(@RequestBody OrderCreateDto orderCreateDto) {

        return ResponseEntity.ok(itemService.addOrderItemToOrder(
                orderCreateDto.customerId(),
                orderCreateDto.restaurantId(),
                orderCreateDto.orderItems()));

    }

    @PutMapping("{orderId}/customerConfirm")
    public ResponseEntity<OrderShowDto> customerConfirmOrder(@PathVariable("orderId") Long orderId,
            @RequestParam("addressId") Long addressId) {

        return ResponseEntity.ok(orderService.customerChangeOrderStatus(
                orderId,
                addressId,
                OrderStatusEnum.PENDING.status,
                OrderStatusEnum.CART.status));

    }

    @PutMapping("{orderId}/deliveryAccept")
    public ResponseEntity<OrderShowDto> deliveryAcceptOrder(@PathVariable("orderId") Long orderId,
            @RequestParam("deliveryId") Long deliveryId) {

        return ResponseEntity.ok(orderService.deliveryAcceptOrder(orderId, deliveryId));
    }

    @PutMapping("{orderId}/orderDelivered")
    public ResponseEntity<OrderShowDto> orderDelivered(@PathVariable("orderId") Long orderId,
            @RequestParam("deliveryId") Long deliveryId) {

        return ResponseEntity.ok(orderService.orderDelivered(orderId, deliveryId));
    }

    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<OrderShowDto>> getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    @GetMapping("readyToPickup/delivery/{deliveryId}")
    public ResponseEntity<List<OrderShowDto>> getReadyToPickupOrdersByDelivery(
            @PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity
                .ok(orderService.getOrdersByStatusAndDelivery(deliveryId, OrderStatusEnum.READY_TO_PICKUP.status));
    }

}
