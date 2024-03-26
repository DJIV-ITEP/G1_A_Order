package com.food_delivery.g1_a_order.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.OrderItemService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orderItem")
public class OrderItemController {

    OrderItemService itemService;

    @DeleteMapping("{id}/delete")
    public ResponseEntity<String> deleteOrderItem(@PathVariable("id") Long id) {

        itemService.deleteOrderItem(id);
        return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
    }

    @GetMapping("order/{orderId}")
    public List<OrderItemShowDto> getOrderItemByOrder(@PathVariable("orderId") Long orderId) {
        
        return itemService.getOrderItemByOrder(orderId);

        
    }
    
}
