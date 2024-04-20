package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.response.ErrResponse;
import com.food_delivery.g1_a_order.service.OrderItemService;
import com.food_delivery.g1_a_order.service.OrderService;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderCustomerController {

        private final OrderService orderService;
        private final OrderItemService itemService;

        /**
         * confirmDto
         */
        private record customerConfirmDto(
                        Long orderId,
                        Long addressId) {
        }

        @PostMapping("customer/add/item")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Order item successfully added to order", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Customer id, restaurant id or item is missing or there is an item exists from another restaurant or order already confirmed", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
        public ResponseEntity<OrderShowDto> addOrderItems(@RequestBody OrderCreateDto orderCreateDto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(itemService.addOrderItemToOrder(
                                                orderCreateDto.customerId(),
                                                orderCreateDto.restaurantId(),
                                                orderCreateDto.orderItems()));

        }

        @PutMapping("customer/setOrderAddress")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = OrderShowDto.class))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id or customer address not found", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
                        @ApiResponse(responseCode = "406", description = "Order is incomplete or address does not belong to customer", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
        })
        public ResponseEntity<OrderShowDto> customerSetOrderAddresses(@RequestBody customerConfirmDto body) {
                Long orderId = body.orderId();
                Long addressId = body.addressId();

                return ResponseEntity.ok(orderService.customerSetOrderAddresses(
                                orderId,
                                addressId));

        }

        @GetMapping("customer/{customerId}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderShowDto.class)))),
                        @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
        })
        public ResponseEntity<List<OrderShowDto>> getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
                return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
        }

}
