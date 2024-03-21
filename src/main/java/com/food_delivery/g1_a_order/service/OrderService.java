package com.food_delivery.g1_a_order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;

    public List<OrderShowDto> getOrders() {

        List<Order> orders = orderRepository.findAll();
        // System.out.println("_____________________________debug_____________________________");
        // System.out.println(orders.get(0).toString());
        return orderMapper.toOrderShowDto(orders);
    }

    public boolean createOrder(OrderCreateDto OrderCreateDto) {
        
        Order order = orderMapper.toOrder(OrderCreateDto);
        order.setOrderStatus(OrderStatusEnum.PENDING.status);
        // order.setOrderItems();

        List<OrderItem> orderItems = orderItemMapper.toOrderItem(OrderCreateDto.orderItems());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        order.setOrderItems(orderItems);

        return orderRepository.save(order) == order;
    }

}
