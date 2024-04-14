package com.food_delivery.g1_a_order.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

        /**
         * restaurantConfirmDto
         */
        private record restaurantConfirmDto(
                        Long orderId,
                        Long restaurantId) {
        }

        @PutMapping("restaurant/startPreparing")
        public ResponseEntity<OrderShowDto> restaurantStartPreparingOrder(
                        @RequestBody restaurantConfirmDto body) {

                Long orderId = body.orderId();
                Long restaurantId = body.restaurantId();

                return ResponseEntity.ok(orderService.restaurantChangeOrderStatus(
                                orderId,
                                restaurantId,
                                OrderStatusEnum.IN_PEOGRESS.status,
                                OrderStatusEnum.PENDING.status));
        }

        @PutMapping("restaurant/readyToPickup")
        public ResponseEntity<OrderShowDto> restaurantCompleteOrder(
                        @RequestBody restaurantConfirmDto body) {

                Long orderId = body.orderId();
                Long restaurantId = body.restaurantId();

                return ResponseEntity.ok(orderService.restaurantChangeOrderStatus(
                                orderId,
                                restaurantId,
                                OrderStatusEnum.READY_TO_PICKUP.status,
                                OrderStatusEnum.IN_PEOGRESS.status));
        }

        @PutMapping("restaurant/cancel")
        public ResponseEntity<OrderShowDto> restaurantCancel(
                        @RequestBody restaurantConfirmDto body) {

                Long orderId = body.orderId();
                Long restaurantId = body.restaurantId();

                return ResponseEntity.ok(orderService.restaurantChangeOrderStatus(
                                orderId,
                                restaurantId,
                                OrderStatusEnum.CANCELED.status,
                                OrderStatusEnum.IN_PEOGRESS.status));
        }

        @PutMapping("restaurant/reject")
        public ResponseEntity<OrderShowDto> restaurantReject(
                        @RequestBody restaurantConfirmDto body) {

                Long orderId = body.orderId();
                Long restaurantId = body.restaurantId();

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
                                .ok(orderService.getOrdersByStatusAndRestaurant(restaurantId,
                                                OrderStatusEnum.PENDING.status));
        }

        @GetMapping("inProgress/restaurant/{restaurantId}")
        public ResponseEntity<List<OrderShowDto>> getInProgressOrdersByRestaurant(
                        @PathVariable("restaurantId") Long restaurantId) {
                return ResponseEntity
                                .ok(orderService.getOrdersByStatusAndRestaurant(restaurantId,
                                                OrderStatusEnum.IN_PEOGRESS.status));
        }

}
