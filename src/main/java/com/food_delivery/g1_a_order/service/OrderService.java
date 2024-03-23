package com.food_delivery.g1_a_order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.enum_.ResponseMsg;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public List<OrderShowDto> getOrders() {

        List<Order> orders = orderRepository.findAll();
        return orderMapper.toOrderShowDto(orders);
    }

    public boolean createOrder(OrderCreateDto OrderCreateDto) {

        Order order = orderMapper.toOrder(OrderCreateDto);
        order.setOrderStatus(OrderStatusEnum.PENDING.status);

        List<OrderItem> orderItems = orderItemMapper.toOrderItem(OrderCreateDto.orderItems());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        order.setOrderItems(orderItems);

        return orderRepository.save(order).equals(order);
    }

    public OrderShowDto changeOrderStatus(Long orderId, Long orderStatusId) {

        Order order = null;
        OrderStatus status = null;

        try {

            order = orderRepository.findById(orderId).get();

            status = orderStatusRepository.findById(orderStatusId).get();

        } catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.notFound("no order nither status found");

        }

        // order = Order.builder().id(orderId).orderStatus(status).build();

        order.setOrderStatus(status);
        order = orderRepository.save(order);

        OrderShowDto dto = orderMapper
                .toOrderShowDto(order);

        return dto;

    }

}
