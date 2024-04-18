package com.food_delivery.g1_a_order.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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

    @InjectMocks
    private OrderItemService underTest;

    @Test
    void testAddOrderItemToOrderWhenOrderExist() {

        Long customerId = 1L;
        Long restaurantId = 1L;

        OrderItem item = OrderItem.builder()
                .quantity(1l)
                .itemId(1l)
                .build();

        List<OrderItemsCreateDto> itemDto = List.of();

        Order order = Order.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .orderStatus(OrderStatusEnum.CART.status)
                .orderItems(List.of(item))
                .build();

        OrderShowDto actual = orderMapper.toOrderShowDto(order);

        when(orderMapper.toOrderShowDto(any(Order.class))).thenReturn(actual);

        when(orderRepository.findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId,
                OrderStatusEnum.CART.status))
                .thenReturn(Optional.of(order));

        when(orderService.addNewOrderItemsToOrder(itemDto, order.getId())).thenReturn(order);

        OrderShowDto result = underTest.addOrderItemToOrder(customerId, restaurantId, itemDto);

        assertThat(result).isEqualTo(actual);

        verify(orderRepository, times(1)).findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId,
                OrderStatusEnum.CART.status);
        verify(orderService, times(1)).addNewOrderItemsToOrder(itemDto, order.getId());

    }

    @Test
    public void testAddOrderItemToOrderWhenOrderDoesNotExist() {
    
        Long customerId = 1L;
        Long restaurantId = 1L;
    
        OrderItem item = OrderItem.builder()
                .quantity(1l)
                .itemId(1l)
                .build();
    
        List<OrderItemsCreateDto> itemDto = new ArrayList<>();
    
        Order order = Order.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .orderStatus(OrderStatusEnum.CART.status)
                .orderItems(List.of(item))
                .build();
    
        when(orderRepository.findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId,
                OrderStatusEnum.CART.status))
                .thenReturn(Optional.empty());
    
        OrderCreateDto newOrderDto = orderMapper.toOrderCreateDto(order);
        when(orderMapper.toOrderCreateDto(any(Order.class))).thenReturn(newOrderDto);
    
        OrderShowDto actual = orderMapper.toOrderShowDto(order);
        when(orderMapper.toOrderShowDto(any(Order.class))).thenReturn(actual);
    
        when(orderService.createOrder(newOrderDto)).thenReturn(order);
    
        when(orderService.addNewOrderItemsToOrder(itemDto, order.getId())).thenReturn(order);
    
        OrderShowDto result = underTest.addOrderItemToOrder(customerId, restaurantId, itemDto);
    
        assertThat(result).isEqualTo(actual);
    
        verify(orderRepository, times(1)).findFirstByCustomerIdAndOrderStatusOrderByCreatedAtAsc(customerId,
                OrderStatusEnum.CART.status);
        verify(orderService, times(1)).createOrder(newOrderDto);
        verify(orderService, times(1)).addNewOrderItemsToOrder(itemDto, order.getId());
    }
    @Test
    public void testDeleteOrderItem() {

        OrderItem item = OrderItem.builder().itemId(1L).quantity(10L).build();
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.CART.status);
        order.setOrderItems(List.of(item));
        item.setOrder(order);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        underTest.deleteOrderItem(1L);

        verify(itemRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    public void testDeleteOrderItemWhenOrderHasMultipleItems() {

        Long itemId = 2L;

        OrderItem item1 = OrderItem.builder().itemId(1L).quantity(10L).build();
        OrderItem item2 = OrderItem.builder().itemId(1L).quantity(10L).build();

        List<OrderItem> items = Stream.of(item1, item2).collect(Collectors.toList());

        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.CART.status);
        order.setOrderItems(items);
        item2.setOrder(order);
        item1.setOrder(order);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item2));

        underTest.deleteOrderItem(itemId);

        verify(itemRepository, times(1)).findById(itemId);
        verify(orderRepository, times(1)).saveAndFlush(order);
        verify(itemRepository, times(1)).deleteById(itemId);
    }

    @Test
    // @Disabled
    public void testGetOrderItemByOrder() {
        Long orderId = 1L;

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        order.setOrderItems(orderItems);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        List<OrderItemShowDto> orderItemShowDtos = new ArrayList<>();
        when(itemMapper.toOrderItemShowDto(orderItems)).thenReturn(orderItemShowDtos);

        List<OrderItemShowDto> result = underTest.getOrderItemByOrder(orderId);

        assertThat(result).isEqualTo(orderItemShowDtos);
        verify(orderRepository, times(1)).findById(orderId);
        verify(itemMapper, times(1)).toOrderItemShowDto(orderItems);
    }

}
