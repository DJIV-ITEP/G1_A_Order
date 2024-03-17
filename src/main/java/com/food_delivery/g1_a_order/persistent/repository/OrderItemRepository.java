package com.food_delivery.g1_a_order.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_delivery.g1_a_order.persistent.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
