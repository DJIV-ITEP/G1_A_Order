package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.order.OrderShowDto;
import com.food_delivery.g1_a_order.api.dto.payment.PaymentCreateDto;
import com.food_delivery.g1_a_order.config.mapper.OrderMapper;
import com.food_delivery.g1_a_order.config.mapper.PaymentMapper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.Payment;
import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentMethodEnum;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderItemRepository;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import com.food_delivery.g1_a_order.persistent.repository.PaymentMethodRepository;
import com.food_delivery.g1_a_order.persistent.repository.PaymentRepository;
import com.food_delivery.g1_a_order.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPaymentService extends BaseService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderShowDto processCashPayment(PaymentCreateDto paymentCreateDto) {
        Long orderId = paymentCreateDto.orderId();
        PaymentMethod paymentMethod = PaymentMethodEnum.COD.paymentMethod;

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> handleNotFound("Order not found"));

        if (order.getOrderStatus().getSequence() != OrderStatusEnum.CART.status.getSequence())
            throw handleNotAcceptable("Order status is not CART");

        double paymentAmount = order.getTotalPrice();
        // Validate input parameters
        return this.confirmPayment(order, paymentMethod, paymentAmount);
    }

    private OrderShowDto confirmPayment(Order order, PaymentMethod paymentMethod, Double paymentAmount) {

        if (order.getAddress() == null)
            handleNotFound("Customer address not found");

        // Create new Payment object
        Payment payment = Payment.builder()
                .customerId(order.getCustomerId())
                .amount(paymentAmount)
                .description("#transaction for order" + order.getId())
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatusEnum.PENDING.status)
                .build();

        // Save Payment object to database
        Payment savedPayment = paymentRepository.save(payment);
        // Set payment object in Order entity
        order.setPayment(savedPayment);
        // Save Order object to database
        Order savedOrder = orderRepository.save(order);
        
        // Set Order status to pinding and Return Payment object as response
        return orderService.changeOrderStatus(savedOrder, OrderStatusEnum.PENDING.status);
    }
}