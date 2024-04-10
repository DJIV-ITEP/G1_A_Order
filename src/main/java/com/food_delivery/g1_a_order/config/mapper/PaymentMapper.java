package com.food_delivery.g1_a_order.config.mapper;

import com.food_delivery.g1_a_order.api.dto.payment.PaymentCreateDto;
import com.food_delivery.g1_a_order.api.dto.payment.PaymentShowDto;
import com.food_delivery.g1_a_order.persistent.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    // payment create dto
    Payment toPayment(PaymentCreateDto paymentCreateDto);
    PaymentCreateDto toPaymentCreateDto(Payment payment);

    // payment show dto
    Payment toPayment(PaymentShowDto paymentShowDto);
    PaymentShowDto toPaymentShowDto(Payment payment);
    List<PaymentShowDto> toPaymentShowDto(List<Payment> payment);
    List<Payment> toPayment(List<PaymentShowDto> paymentShowDto);
}
