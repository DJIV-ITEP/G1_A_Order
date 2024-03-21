package com.food_delivery.g1_a_order.service;

import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository itemRepository;

    public boolean deleteOrderItem(Long id) {

        if (itemRepository.findById(id).isPresent()) {
            try {
                itemRepository.deleteById(id);

            } catch (IllegalArgumentException e) {
                System.out.println(e);
                return false;

            }
            return true;
        }

        return false;

    }

}
