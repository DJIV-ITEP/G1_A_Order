package com.food_delivery.g1_a_order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderStatusMapper;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository itemRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderService orderService;
    @Mock
    private OrderItemMapper itemMapper;
    @Mock
    private OrderMapper orderMapper;

    private OrderItemService underTest;

    @BeforeEach
    void setUp() {
        underTest = new OrderItemService(itemRepository, orderRepository, orderService, itemMapper, orderMapper);
    }

    @Test
    @Disabled
    void testAddOrderItemToOrder() {

        

    }

    @Test
    @Disabled
    void testDeleteOrderItem() {

    }

    @Test
    @Disabled
    void testGetOrderItemByOrder() {

    }
}
