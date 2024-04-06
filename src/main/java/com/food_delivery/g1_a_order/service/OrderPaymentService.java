package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.payment.PaymentCreateDto;
import com.food_delivery.g1_a_order.api.dto.payment.PaymentShowDto;
import com.food_delivery.g1_a_order.config.mapper.PaymentMapper;
import com.food_delivery.g1_a_order.persistent.entity.Order;
import com.food_delivery.g1_a_order.persistent.entity.Payment;
import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderRepository;
import com.food_delivery.g1_a_order.persistent.repository.PaymentMethodRepository;
import com.food_delivery.g1_a_order.persistent.repository.PaymentRepository;
import com.food_delivery.g1_a_order.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPaymentService extends BaseService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    public PaymentShowDto processPayment(PaymentCreateDto paymentCreateDto) {
        Long orderId= paymentCreateDto.orderId();
        Long paymentMethodId = paymentCreateDto.paymentMethodId();
        Double paymentAmount =0.0;
        // Validate input parameters
        Order order = orderRepository.findById(orderId).orElseThrow(() ->  handleNotFound("Order not found"));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> handleNotFound("Payment method not found"));
        return this.confirmPayment(order,paymentMethod,paymentAmount);
    }
    
    private PaymentShowDto confirmPayment(Order  order, PaymentMethod  paymentMethod, Double paymentAmount) {
        // Create new Payment object
        Payment payment = Payment.builder()
                .customerId(order.getCustomerId())
                .amount(paymentAmount)
                .description("#transaction for order"+order.getId())
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatusEnum.PENDING.status)
                .build();
        
        // Save Payment object to database
        Payment savedPayment = paymentRepository.save(payment);
        // Set payment object in Order entity
        order.setPayment(savedPayment);
        order.setOrderStatus(OrderStatusEnum.IN_PEOGRESS.status);
        // Save Order object to database
        Order savedOrder = orderRepository.save(order);
        // Return Payment object as response
        return paymentMapper.toPaymentShowDto(savedPayment);
    }
}