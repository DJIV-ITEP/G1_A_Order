package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.service.OrderItemService;
import com.food_delivery.g1_a_order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("customer/setOrderAddress")
    public ResponseEntity<OrderShowDto> customerSetOrderAddresses(@RequestBody customerConfirmDto body) {
        Long orderId = body.orderId();
        Long addressId = body.addressId();

        return ResponseEntity.ok(orderService.customerSetOrderAddresses(
                orderId,
                addressId));

    }

    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<OrderShowDto>> getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

}
