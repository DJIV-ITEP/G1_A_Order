package com.food_delivery.g1_a_order.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.food_delivery.g1_a_order.config.mapper.OrderStatusMapper;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;

@ExtendWith(MockitoExtension.class)
public class OrderStatusServiceTest {

    @Mock
    private OrderStatusRepository repository;

    @Mock
    private OrderStatusMapper mapper;

    @InjectMocks
    private OrderStatusService underTest;

    // @BeforeEach
    // void setUp() {
    //     underTest = new OrderStatusService(repository, mapper);
    // }

    @Test
    void testGetOrderStatus() {

        underTest.getOrderStatus();

        verify(repository).findAll();

    }
}
