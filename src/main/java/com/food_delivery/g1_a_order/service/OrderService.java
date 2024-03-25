package com.food_delivery.g1_a_order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
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

    private final WebClient customerEndpoint;

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

        if (order.getOrderStatus().getSequence() > status.getSequence())
            StatusResponseHelper.notAcceptable("status is not acceptable");

        if (order.getOrderStatus().getSequence() + 1 != status.getSequence())
            StatusResponseHelper.notAcceptable("status is not acceptable");

        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        OrderShowDto dto = orderMapper
                .toOrderShowDto(order);

        return dto;

    }

    // confirm order
    public OrderShowDto confirmOrder(Long orderId) {

        Order order = null;

        try {
            order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new Exception("no order found"));

            if (null == order.getCustomerId() || null == order.getRestaurantId()
                    || null == order.getCustomerAddressId())
                StatusResponseHelper.notAcceptable("order is incomplete");

            if (order.getOrderItems().isEmpty())
                StatusResponseHelper.notAcceptable("order should have at least one item");

            // get customer address
            Long customerAddressId = customerEndpoint.get()
                .uri("/customer/address/" + order.getCustomerId())
                .retrieve()
                .bodyToMono(Long.class)
                .block();

            if (null == customerAddressId)
                StatusResponseHelper.notAcceptable("customer address not found");

            order.setCustomerAddressId(customerAddressId);

            // change order status to PENDING status and save
            order.setOrderStatus(OrderStatusEnum.PENDING.status);
            order.setUpdatedAt(LocalDateTime.now());
            order = orderRepository.save(order);

        } catch (Exception e) {
            System.err.println(e);
            StatusResponseHelper.notFound("no order found");
        }

        OrderShowDto dto = orderMapper
                .toOrderShowDto(order);

        return dto;
    }
}