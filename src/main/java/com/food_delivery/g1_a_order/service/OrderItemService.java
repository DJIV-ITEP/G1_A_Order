package com.food_delivery.g1_a_order.service;

import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public boolean deleteOrderItem(Long id) {

        if (itemRepository.findById(id).isPresent()) {

            OrderItem item = itemRepository.findById(id).get();

            // System.out.println("________debug(size)_________");
            // System.out.println(item.getOrder().getId());
            // System.out.println("________debug_________");


            try {
                itemRepository.deleteById(id);

                if ( 0 == item.getOrder().getOrderItems().size()) {
                    orderRepository.deleteById(item.getOrder().getId());
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e);
                return false;

            }
            return true;
        }

        return false;

    }

}
