package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.OrderItem;
import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.AddressRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final AddressRepository addressRepository;
    private final WebClient customerEndpoint;

    @Transactional
    public List<OrderShowDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        System.out.println("orders orders orders orders" + orders.size());
        return orderMapper.toOrderShowDto(orders);
    }

    @Transactional
    public Order createOrder(OrderCreateDto OrderCreateDto) {
        Order order = orderMapper.toOrder(OrderCreateDto);
        // List<OrderItem> orderItems =
        // orderItemMapper.toOrderItem(OrderCreateDto.orderItems());
        // orderItems.forEach(orderItem -> orderItem.setOrder(order));
        // order.setOrderItems(orderItems);
        return orderRepository.saveAndFlush(order);
    }

    @Transactional
    public Order addNewOrderItemsToOrder(List<OrderItemsCreateDto> itemsCreateDtos, Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> StatusResponseHelper.getNotFound("no order found"));

        List<OrderItem> dtoItems = orderItemMapper.toOrderItem(itemsCreateDtos);
        List<OrderItem> orderItem = order.getOrderItems();
        
        if (orderItem != null) {
            orderItem.addAll(dtoItems);
            order.setOrderItems(orderItem);
        } else
            order.setOrderItems(dtoItems);

        dtoItems.forEach(Item -> Item.setOrder(order));

        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.saveAndFlush(order);
    }

    @Transactional
    public OrderShowDto changeOrderStatus(Long orderId, Long orderStatusId) {
        Order order = null;
        OrderStatus status = null;
        try {

            order = orderRepository.findById(orderId).get();
            status = orderStatusRepository.findById(orderStatusId).get();

        } catch (Exception e) {

            System.out.println(e);
            StatusResponseHelper.notFound("no order neither status found");

        }

        if (order.getOrderStatus().getSequence() > status.getSequence())
            StatusResponseHelper.notAcceptable("status is not acceptable");

        if (order.getOrderStatus().getSequence() + 1 != status.getSequence())
            StatusResponseHelper.notAcceptable("status is not acceptable");

        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        return orderMapper
                .toOrderShowDto(order);

    }

    // confirm order
    @Transactional
    public boolean confirmOrder(Long orderId, Long customerAddressId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> StatusResponseHelper.getNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            StatusResponseHelper.notAcceptable("Order status is not CART");

        if (order.getCustomerId() == null || order.getRestaurantId() == null)
            StatusResponseHelper.notAcceptable("Order is incomplete");

        if (order.getOrderItems().isEmpty())
            StatusResponseHelper.notAcceptable("Order should have at least one item");

        if (customerAddressId == null || !addressExists(customerAddressId))
            StatusResponseHelper.notFound("Customer address not found");

        System.out.println(" customerAddressId customerAddressId customerAddressId: " + customerAddressId);
        order.setAddress(addressRepository.findById(customerAddressId).get());
        order.setOrderStatus(OrderStatusEnum.PENDING.status);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return true;
    }

    public boolean addressExists(Long addressId) {
        return addressRepository.existsById(addressId);
    }
}
