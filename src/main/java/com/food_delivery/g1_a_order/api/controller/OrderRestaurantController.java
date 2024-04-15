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
import com.food_delivery.g1_a_order.api.dto.response.ErrResponse;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.service.OrderService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

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
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Order status is not as expected or order does not belong to restaurant", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
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
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Order status is not as expected or order does not belong to restaurant", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
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
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Order status is not as expected or order does not belong to restaurant", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
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
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Order status is not as expected or order does not belong to restaurant", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
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
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderShowDto.class)))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
        })
        public ResponseEntity<List<OrderShowDto>> getPendingOrdersByRestaurant(
                        @PathVariable("restaurantId") Long restaurantId) {
                return ResponseEntity
                                .ok(orderService.getOrdersByStatusAndRestaurant(restaurantId,
                                                OrderStatusEnum.PENDING.status));
        }

        @GetMapping("inProgress/restaurant/{restaurantId}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderShowDto.class)))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
        })
        public ResponseEntity<List<OrderShowDto>> getInProgressOrdersByRestaurant(
                        @PathVariable("restaurantId") Long restaurantId) {
                return ResponseEntity
                                .ok(orderService.getOrdersByStatusAndRestaurant(restaurantId,
                                                OrderStatusEnum.IN_PEOGRESS.status));
        }

}
