package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.Address;
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

    // @Transactional
    public OrderShowDto changeOrderStatus(Order order, OrderStatus status) {

        // if (order.getOrderStatus().getSequence() > status.getSequence())
        //     handleNotAcceptable("status is not acceptable");

        // if (order.getOrderStatus().getSequence() + 1 != status.getSequence())
        //     handleNotAcceptable("status is not acceptable");

        if (order.getOrderItems().isEmpty())
            handleNotAcceptable("Order should have at least one item");

        if (order.getAddress() == null)
            handleNotFound("Customer address not found");

        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        return orderMapper
                .toOrderShowDto(orderRepository.saveAndFlush(order));

    }

    // todo: handle get Restaurant address from restaurant service
    @Transactional
    public OrderShowDto customerChangeOrderStatus(
            Long orderId,
            Long customerAddressId,
            OrderStatus newStatus,
            OrderStatus currentStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != currentStatus.getSequence())
            handleNotAcceptable("Order status is not " + currentStatus.getValue());

        if (order.getCustomerId() == null || order.getRestaurantId() == null)
            handleNotAcceptable("Order is incomplete");

        if (customerAddressId == null || !addressService.addressExists(customerAddressId))
            handleNotFound("Customer address not found");

        // if (order.getRestaurantAddressId()==null )
        // handleNotFound("Restaurant address not found");

        Address address = addressRepository.findById(customerAddressId).get();

        if (address.getCustomerId() != order.getCustomerId())
            handleNotAcceptable("Address does not belong to customer");

        order.setAddress(address);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.saveAndFlush(order);

    @Transactional
    public OrderShowDto restaurantStartPreparingOrder(Long orderId, Long restaurantId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.PENDING.status.getSequence())
            handleNotAcceptable("Order status is not " + OrderStatusEnum.PENDING.status.getValue());

        if (order.getRestaurantId() != restaurantId)
            handleNotAcceptable("Order does not belong to restaurant");

        if (order.getOrderItems().isEmpty())
            handleNotAcceptable("Order should have at least one item");

        if (order.getAddress() == null)
            handleNotFound("Customer address not found");

        order.setOrderStatus(OrderStatusEnum.IN_PEOGRESS.status);
        order.setUpdatedAt(LocalDateTime.now());
        // orderRepository.save(order);
        return orderMapper.toOrderShowDto(orderRepository.saveAndFlush(order));
    }

    @Transactional
    public OrderShowDto restaurantCompleteOrder(Long orderId, Long restaurantId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.IN_PEOGRESS.status.getSequence())
            handleNotAcceptable("Order status is not " + OrderStatusEnum.IN_PEOGRESS.status.getValue());

        if (order.getRestaurantId() != restaurantId)
            handleNotAcceptable("Order does not belong to restaurant");

        if (order.getOrderItems().isEmpty())
            handleNotAcceptable("Order should have at least one item");

        if (order.getAddress() == null)
            handleNotFound("Customer address not found");

        order.setOrderStatus(OrderStatusEnum.READY_TO_PICKUP.status);
        order.setUpdatedAt(LocalDateTime.now());
        // orderRepository.save(order);
        return orderMapper.toOrderShowDto(orderRepository.saveAndFlush(order));
    }


    // todo: handle get deliveryId from delivery service
    @Transactional
    public OrderShowDto deliveryAcceptOrder(Long orderId, Long deliveryId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.READY_TO_PICKUP.status.getSequence())
            handleNotAcceptable("Order status is not " + OrderStatusEnum.READY_TO_PICKUP.status.getValue());

        if (order.getOrderItems().isEmpty())
            handleNotAcceptable("Order should have at least one item");

        if (order.getAddress() == null)
            handleNotFound("Customer address not found");

        order.setDeliveryId(deliveryId);

        order.setOrderStatus(OrderStatusEnum.ON_THE_WAY.status);
        order.setUpdatedAt(LocalDateTime.now());
        // orderRepository.save(order);
        return orderMapper.toOrderShowDto(orderRepository.saveAndFlush(order));
    }


    @Transactional
    public OrderShowDto orderDelivered(Long orderId, Long deliveryId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.ON_THE_WAY.status.getSequence())
            handleNotAcceptable("Order status is not " + OrderStatusEnum.ON_THE_WAY.status.getValue());

        if (order.getOrderItems().isEmpty())
            handleNotAcceptable("Order should have at least one item");

            if (order.getDeliveryId() != deliveryId)
            handleNotAcceptable("Order does not belong to delivery");

        if (order.getAddress() == null)
            handleNotFound("Customer address not found");


        order.setOrderStatus(OrderStatusEnum.DELIVERED.status);
        order.setUpdatedAt(LocalDateTime.now());
        // orderRepository.save(order);
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

}
