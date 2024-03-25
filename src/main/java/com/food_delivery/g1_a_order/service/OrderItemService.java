package com.food_delivery.g1_a_order.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public boolean deleteOrderItem(Long id) {

        OrderItem item = null;
        try {

            item = itemRepository.findById(id).get();

            itemRepository.deleteById(id);

            if (0 == item.getOrder().getOrderItems().size());
            orderRepository.deleteById(item.getOrder().getId());

        } catch (Exception e) {
            System.out.println(e);
            StatusResponseHelper.notFound("no item nither order found");
            return false;
        }

        return true;

    }

}
