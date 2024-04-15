package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.orderStatus.OrderStatusShowDto;
import com.food_delivery.g1_a_order.config.mapper.OrderStatusMapper;
import com.food_delivery.g1_a_order.service.OrderStatusService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.food_delivery.g1_a_order.api.dto.response.ErrResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orderStatus")
public class OrderStatusController {

    private final OrderStatusService service;

    @Autowired
    OrderStatusMapper orderStatusMapper;

    @GetMapping()
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderStatusShowDto.class)))),
})
    public ResponseEntity<List<OrderStatusShowDto>> getOrderStatus() {

        List<OrderStatusShowDto> orderStatus = service.getOrderStatus();

        return ResponseEntity.ok(orderStatus);
    }

}
