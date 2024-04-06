package com.food_delivery.g1_a_order.config.mapper;

import com.food_delivery.g1_a_order.api.dto.paymentStatus.PaymentStatusShowDto;
import com.food_delivery.g1_a_order.persistent.entity.PaymentStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentStatusMapper {
    // payment status
    PaymentStatusShowDto toPaymentStatusShowDto(PaymentStatus paymentStatus);
    List<PaymentStatusShowDto> toPaymentStatusShowDto(List<PaymentStatus> paymentStatus);
}
