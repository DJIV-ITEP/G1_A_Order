package com.food_delivery.g1_a_order.service;

import com.food_delivery.g1_a_order.api.dto.paymentMethod.PaymentMethodCreateDto;
import com.food_delivery.g1_a_order.api.dto.paymentMethod.PaymentMethodShowDto;
import com.food_delivery.g1_a_order.config.mapper.PaymentMethodMapper;
import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import com.food_delivery.g1_a_order.persistent.repository.PaymentMethodRepository;
import com.food_delivery.g1_a_order.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService extends BaseService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    public PaymentMethodShowDto createPaymentMethod(PaymentMethodCreateDto paymentMethodCreateDto) {
        PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(paymentMethodCreateDto);
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toPaymentMethodShowDto(savedPaymentMethod);
    }
    public PaymentMethodShowDto updatePaymentMethod(Long id, PaymentMethodCreateDto paymentMethodCreateDto) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() ->  handleNotFound("Payment method not found"));

        paymentMethod.setName(paymentMethodCreateDto.name());
        paymentMethod.setEnabled(paymentMethodCreateDto.enabled());

        PaymentMethod updatedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toPaymentMethodShowDto(updatedPaymentMethod);
    }

    public List<PaymentMethodShowDto> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethodMapper.toPaymentMethodShowDto(paymentMethods);
    }

    public List<PaymentMethodShowDto> getEnabledPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByEnabledTrue().orElseThrow(() -> handleNotFound("not found enabled payment methods"));
        return paymentMethodMapper.toPaymentMethodShowDto(paymentMethods);
    }
}