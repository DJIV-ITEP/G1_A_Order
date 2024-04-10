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
import com.food_delivery.g1_a_order.service.base.BaseService;

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
public class OrderService extends BaseService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    private final WebClient customerEndpoint;

    @Transactional
    public List<OrderShowDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
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
    public OrderShowDto customerConfirmOrder(Long orderId, Long customerAddressId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> StatusResponseHelper.getNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            StatusResponseHelper.notAcceptable("Order status is not CART");

        if (order.getCustomerId() == null || order.getRestaurantId() == null)
            StatusResponseHelper.notAcceptable("Order is incomplete");

        if (order.getOrderItems().isEmpty())
            StatusResponseHelper.notAcceptable("Order should have at least one item");

        if (customerAddressId == null || !addressService.addressExists(customerAddressId))
            StatusResponseHelper.notFound("Customer address not found");

        System.out.println(" customerAddressId customerAddressId customerAddressId: " + customerAddressId);
        order.setAddress(addressRepository.findById(customerAddressId).get());
        order.setOrderStatus(OrderStatusEnum.PENDING.status);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return orderMapper.toOrderShowDto(orderRepository.saveAndFlush(order));
    }

    @Transactional
    public List<OrderShowDto> getOrdersByCustomer(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId)
                .orElseThrow(() -> handleNotFound("no order found"));
        return orderMapper.toOrderShowDto(orders);
    }

    @Transactional
    public List<OrderShowDto> getOrdersByStatusAndRestaurant(Long restaurantId, OrderStatus status) {
        List<Order> orders = orderRepository.findByRestaurantIdAndOrderStatusOrderByUpdatedAtAsc(restaurantId, status)
                .orElseThrow(() -> handleNotFound("no order found"));
        return orderMapper.toOrderShowDto(orders);
    }

    @Transactional
    public List<OrderShowDto> getOrdersByStatusAndDelivery(Long deliveryId, OrderStatus status) {

        List<Order> orders = orderRepository.findByDeliveryIdAndOrderStatusOrderByUpdatedAtAsc(deliveryId, status)
                .orElseThrow(() -> handleNotFound("no order found"));
        return orderMapper.toOrderShowDto(orders);
    }

    @Transactional
    public OrderShowDto assignDeliveryToOrder(Long orderId, Long deliveryId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("no order found"));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.READY_TO_PICKUP.status.getSequence())
            handleNotAcceptable("Order status is not " + OrderStatusEnum.READY_TO_PICKUP.status.getValue());

        if (null == order.getAddress())
            handleNotAcceptable("Order address is not presented");

        order.setDeliveryId(deliveryId);

        return orderMapper.toOrderShowDto(orderRepository.saveAndFlush(order));

    }
}
