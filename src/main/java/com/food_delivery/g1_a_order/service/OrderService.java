package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.order.OrderCreateDto;
import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.orderItem.OrderItemsCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderItemMapper;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.helper.StatusResponseHelper;
import com.food_delivery.g1_a_order.persistent.entity.*;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentMethodEnum;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.AddressRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;
import com.food_delivery.g1_a_order.persistent.repository.PaymentRepository;
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
    private final EmailService emailService;

    private final WebClient customerEndpoint;

    @Autowired
    private PaymentRepository paymentRepository;

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

    // todo: vlidate item qty and price
    // todo: handle get Restaurant address from restaurant service
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

    // todo: vlidate payment
    @Transactional
    public OrderShowDto changeOrderStatus(Order order, OrderStatus status) {

        // if (order.getOrderStatus().getSequence() > status.getSequence())
        // handleNotAcceptable("status is not acceptable");

        // if (order.getOrderStatus().getSequence() + 1 != status.getSequence())
        // handleNotAcceptable("status is not acceptable");

        if (order.getOrderItems().isEmpty())
            handleNotAcceptable("Order should have at least one item");

        if (order.getAddress() == null)
            handleNotFound("Customer address not found");

        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        // ! send email to customer - don't delete it
        // emailService.sendSimpleMessage(
        //         "ala0alsanea@gmail.com",
        //         "Order with id #" + order.getId() + " Status",
        //         "Your order is " + status.getValue()).subscribe();

        order = changePaymentStausBasedOrderStatusIfCash(order);

        return orderMapper
                .toOrderShowDto(orderRepository.saveAndFlush(order));

    }

    
    private Order changePaymentStausBasedOrderStatusIfCash(Order order) {
        if (order.getOrderStatus().getSequence() == OrderStatusEnum.DELIVERED.status.getSequence()) {
            if (order.getPayment().getPaymentMethod().getRoute()
                    .equals(PaymentMethodEnum.COD.paymentMethod.getRoute())) {
                Payment orderPayment = order.getPayment();
                orderPayment.setPaymentStatus(PaymentStatusEnum.PAID.status);
                paymentRepository.save(orderPayment);
            }
        }
        return order;

    }

    @Transactional
    public OrderShowDto customerSetOrderAddresses(
            Long orderId,
            Long customerAddressId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        if (order.getCustomerId() == null || order.getRestaurantId() == null)
            throwHandleNotAcceptable("Order is incomplete");

        if (customerAddressId == null || !addressService.addressExists(customerAddressId))
            handleNotFound("Customer address not found");

        // if (order.getRestaurantAddressId()==null )
        // handleNotFound("Restaurant address not found");

        Address address = addressRepository.findById(customerAddressId).get();
        if (address.getCustomerId() != order.getCustomerId())
            throwHandleNotAcceptable("Address does not belong to customer");

        order.setAddress(address);
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.saveAndFlush(order);
        return orderMapper.toOrderShowDto(savedOrder);
    }

    @Transactional
    public OrderShowDto restaurantChangeOrderStatus(
            Long orderId,
            Long restaurantId,
            OrderStatus newStatus,
            OrderStatus currentStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        // if (OrderStatusEnum.CANCELED.status.getSequence() == newStatus.getSequence()
        // &&
        // order.getOrderStatus().getSequence() == currentStatus.getSequence())
        // return changeOrderStatus(order, newStatus);

        if (order.getOrderStatus().getSequence() != currentStatus.getSequence())
            handleNotAcceptable("Order status is not " + currentStatus.getValue());

        if (order.getRestaurantId() != restaurantId)
            handleNotAcceptable("Order does not belong to restaurant");

        if (order.getPayment() == null)
            handleNotFound("Payment not found");

        OrderShowDto orderShowDto = changeOrderStatus(order, newStatus);

        return orderShowDto;
    }

    // todo: handle get deliveryId from delivery service
    @Transactional
    public OrderShowDto deliveryChangeOrderStatus(Long orderId,
            Long deliveryId,
            OrderStatus newStatus,
            OrderStatus currentStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("No order found with id: " + orderId));

        if (order.getOrderStatus().getSequence() != currentStatus.getSequence())
            handleNotAcceptable("Order status is not " + currentStatus.getValue());

        if (order.getPayment() == null)
            handleNotFound("Payment not found");

        order.setDeliveryId(deliveryId);

        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.saveAndFlush(order);

        return changeOrderStatus(savedOrder, newStatus);
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
