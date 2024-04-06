package com.food_delivery.g1_a_order.config.mapper;

import com.food_delivery.g1_a_order.api.dto.paymentMethod.PaymentMethodCreateDto;
import com.food_delivery.g1_a_order.api.dto.paymentMethod.PaymentMethodShowDto;
import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    // payment_method create dto
    PaymentMethod toPaymentMethod(PaymentMethodCreateDto paymentMethodCreateDto);
    PaymentMethodCreateDto toPaymentMethodCreateDto(PaymentMethod paymentMethod);

    // paymentMethod show dto
    PaymentMethod toPaymentMethod(PaymentMethodShowDto paymentMethodShowDto);
    PaymentMethodShowDto toPaymentMethodShowDto(PaymentMethod paymentMethod);
    List<PaymentMethodShowDto> toPaymentMethodShowDto(List<PaymentMethod> paymentMethod);
    List<PaymentMethod> toPaymentMethod(List<PaymentMethodShowDto> paymentMethodShowDto);
}
