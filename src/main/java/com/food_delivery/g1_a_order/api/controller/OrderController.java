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
    public ResponseEntity<String> addOrder(@RequestBody OrderCreateDto orderCreateDto) {

        boolean isAdded = itemService.addOrderItemToOrder(
                orderCreateDto.customerId(),
                orderCreateDto.restaurantId(),
                orderCreateDto.orderItems());
        if (isAdded) {
            return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add order");
        }
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

    @PutMapping("{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable("orderId") Long orderId,
            @RequestParam("addressId") Long addressId) {
        boolean isConfirmed = orderService.confirmOrder(orderId, addressId);
        if (isConfirmed) {
            return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to confirm order, please check if the order id and customer address id are correct.");
        }
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
}
