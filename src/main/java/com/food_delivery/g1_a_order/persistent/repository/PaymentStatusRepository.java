package com.food_delivery.g1_a_order.persistent.repository;

import com.food_delivery.g1_a_order.persistent.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
}
