package com.food_delivery.g1_a_order.persistent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long>{

    Optional<Order> findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(Long customerId, OrderStatus orderStatus);
    

}
