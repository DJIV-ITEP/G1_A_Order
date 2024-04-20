package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.food_delivery.g1_a_order.api.dto.response.ErrResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orderItem")
public class OrderItemController {

    private final OrderItemService itemService;

    @DeleteMapping("{itemId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "No item found or order already confirmed", content = @Content(schema = @Schema(implementation = ErrResponse.class)))
    })
    public ResponseEntity<String> deleteOrderItem(@PathVariable("itemId") Long itemId) {

        itemService.deleteOrderItem(itemId);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    @GetMapping("order/{orderId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemShowDto.class)))),
            @ApiResponse(responseCode = "404", description = "No order found with provided id", content = @Content(schema = @Schema(implementation = ErrResponse.class))),
    })
    public ResponseEntity<List<OrderItemShowDto>> getOrderItemByOrder(@PathVariable("orderId") Long orderId) {

        return ResponseEntity.ok(itemService.getOrderItemByOrder(orderId));

    }

}
