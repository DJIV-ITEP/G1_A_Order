package com.food_delivery.g1_a_order.persistent.repository;

import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>{

    Optional<Order> findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(Long customerId, OrderStatus orderStatus);

    Optional<List<Order>> findByCustomerId(Long customerId);
    Optional<List<Order>> findByOrderStatus(OrderStatus orderStatus);
    Optional<List<Order>> findByRestaurantIdAndOrderStatusOrderByUpdatedAtAsc(Long restaurantId,OrderStatus orderStatus);
    Optional<List<Order>> findByDeliveryIdAndOrderStatusOrderByUpdatedAtAsc(Long restaurantId,OrderStatus orderStatus);
}
