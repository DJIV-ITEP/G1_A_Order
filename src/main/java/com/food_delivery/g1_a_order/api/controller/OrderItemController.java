package com.food_delivery.g1_a_order.api.controller;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orderItem")
public class OrderItemController {

    private final OrderItemService itemService;

    @DeleteMapping("{itemId}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable("itemId") Long itemId) {

        itemService.deleteOrderItem(itemId);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<List<OrderItemShowDto>> getOrderItemByOrder(@PathVariable("orderId") Long orderId) {
        
        return ResponseEntity.ok(itemService.getOrderItemByOrder(orderId));

        
    }
    
}
