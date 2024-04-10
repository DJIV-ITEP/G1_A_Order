package com.food_delivery.g1_a_order.persistent.seed;

import com.food_delivery.g1_a_order.persistent.entity.PaymentStatus;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.PaymentStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PaymentStatusSeeder {
    @Bean
    CommandLineRunner commandLinePaymentStatusRunner(PaymentStatusRepository repository) {
        return args -> {
            PaymentStatus pending = PaymentStatusEnum.PENDING.status;
            PaymentStatus paid = PaymentStatusEnum.PAID.status;
            PaymentStatus refund = PaymentStatusEnum.REFUND.status;
            PaymentStatus failed = PaymentStatusEnum.FAILED.status;
            List<PaymentStatus> statuses = List.of(pending, paid, refund, failed);
            repository.saveAll(statuses);
        };
    }
}
