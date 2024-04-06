package com.food_delivery.g1_a_order.persistent.repository;

import com.food_delivery.g1_a_order.persistent.entity.Payment;
import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import com.food_delivery.g1_a_order.persistent.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<List<Payment>> findByPaymentStatus(PaymentStatus paymentStatus);
    Optional<List<Payment>> findByPaymentMethod(PaymentMethod paymentMethod);
    Optional<List<Payment>> findByCustomerId( Long customerId);
}
