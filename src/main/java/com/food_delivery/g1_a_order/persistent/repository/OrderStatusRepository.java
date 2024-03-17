package com.food_delivery.g1_a_order.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
