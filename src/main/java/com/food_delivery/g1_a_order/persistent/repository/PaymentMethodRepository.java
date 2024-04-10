package com.food_delivery.g1_a_order.persistent.repository;

import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<List<PaymentMethod>> findByEnabledTrue();

    Optional<PaymentMethod> findByRoute(String route);

}

