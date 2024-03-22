package com.food_delivery.g1_a_order.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.service.OrderItemService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orderItem")
public class OrderItemController {

    OrderItemService itemService;
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {

        if (itemService.deleteOrderItem(id))
        {
            return ResponseEntity.ok(ResponseMsg.SUCCESS.message);
        }

            return ResponseEntity.ok(ResponseMsg.NOT_FOUND.message);
    }
}
