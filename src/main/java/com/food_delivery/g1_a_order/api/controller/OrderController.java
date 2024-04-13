package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.service.OrderItemService;
import com.food_delivery.g1_a_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // @PostMapping("/customer/{customerId}/restaurant/{restaurantId}/orderItem")
    // public ResponseEntity<String> addOrderItemToOrder(@PathVariable("customerId")
    // Long customerId,
    // @PathVariable("restaurantId") Long restaurantId,
    // @RequestBody List<OrderItemsCreateDto> itemDto) {
    // itemService.addOrderItemToOrder(customerId, restaurantId, itemDto);
    // return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    // }

    @PutMapping("{orderId}/change/orderStatus/{orderStatusId}")
    public ResponseEntity<OrderShowDto> changeOrderStatus(@PathVariable("orderId") Long orderId,
                                                          @PathVariable("orderStatusId") Long orderStatusId) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderStatusId));
    }

    @PutMapping("{orderId}/setOrderAddress")
    public ResponseEntity<OrderShowDto> customerConfirmOrderAddress(@PathVariable("orderId") Long orderId,
                                                             @RequestParam("addressId") Long addressId) {

        return ResponseEntity.ok(orderService.customerConfirmOrderAddress(orderId, addressId));


    }

    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<OrderShowDto>> getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
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

    @GetMapping("readyToPickup/delivery/{deliveryId}")
    public ResponseEntity<List<OrderShowDto>> getReadyToPickupOrdersByDelivery(
            @PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity
                .ok(orderService.getOrdersByStatusAndDelivery(deliveryId, OrderStatusEnum.READY_TO_PICKUP.status));
    }

    @PutMapping("{orderId}/assign/delivery/{deliveryId}")
    public ResponseEntity<OrderShowDto> assignDeliveryToOrder(
            @PathVariable("orderId") Long orderId, @PathVariable("deliveryId") Long deliveryId) {
        return ResponseEntity
                .ok(orderService.assignDeliveryToOrder(orderId, deliveryId));

    }
}
