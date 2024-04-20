package com.food_delivery.g1_a_order.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.service.OrderService;

import lombok.RequiredArgsConstructor;

import com.food_delivery.g1_a_order.api.dto.response.ErrResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderDeliveryController {

        private final OrderService orderService;

        /**
         * deliveryConfirmDto
         */
        private record deliveryConfirmDto(
                        Long orderId,
                        Long deliveryId) {
        }

        @PutMapping("delivery/accept")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id or customer address not found", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Order status is not as expected or order should have at least one item", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
        public ResponseEntity<OrderShowDto> deliveryAcceptOrder(@RequestBody deliveryConfirmDto body) {

                Long orderId = body.orderId();
                Long deliveryId = body.deliveryId();

                return ResponseEntity.ok(orderService.deliveryChangeOrderStatus(
                                orderId,
                                deliveryId,
                                OrderStatusEnum.ON_THE_WAY.status,
                                OrderStatusEnum.READY_TO_PICKUP.status

                ));
        }

        @PutMapping("delivery/delivered")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id or customer address not found", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Order status is not as expected or order should have at least one item", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
        public ResponseEntity<OrderShowDto> orderDelivered(@RequestBody deliveryConfirmDto body) {

                Long orderId = body.orderId();
                Long deliveryId = body.deliveryId();

                return ResponseEntity.ok(orderService.deliveryChangeOrderStatus(
                                orderId,
                                deliveryId,
                                OrderStatusEnum.DELIVERED.status,
                                OrderStatusEnum.ON_THE_WAY.status

                ));
        }

        @GetMapping("readyToPickup/driver/{deliveryId}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderShowDto.class)))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
        })
        public ResponseEntity<List<OrderShowDto>> getReadyToPickupOrdersByDelivery(
                        @PathVariable("deliveryId") Long deliveryId) {
                return ResponseEntity.ok(
                                orderService.getOrdersByStatusAndDelivery(deliveryId,
                                                OrderStatusEnum.READY_TO_PICKUP.status));
        }




        @GetMapping("readyToPickup/delivery")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderShowDto.class)))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
        })
        public ResponseEntity<List<OrderShowDto>> getAllReadyToPickupOrdersByDelivery() {
                return ResponseEntity.ok(
                                orderService.getAllOrdersByStatusAndDelivery(OrderStatusEnum.READY_TO_PICKUP.status));
        }

}
