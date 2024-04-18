package com.food_delivery.g1_a_order.persistent.repository;


import static org.assertj.core.api.Assertions.*;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;


// @AllArgsConstructor
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;


    @Test
    void testFindFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc() {

        orderStatusRepository.save(OrderStatusEnum.CART.status);

        OrderStatus status = orderStatusRepository.findAll().get(0);

        Order order = Order.builder()
                .customerId(1l)
                .restaurantId(1L)
                .orderStatus(status)
                .build();

        OrderItem item = OrderItem.builder()
                .quantity(1L)
                .itemId(1L)
                .order(order)
                .build();

        order.setOrderItems(List.of(item));

        orderRepository.save(order);

        Order expected = orderRepository.findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(1L, status).get();

        assertThat(order).isEqualTo(expected);
    }
}
