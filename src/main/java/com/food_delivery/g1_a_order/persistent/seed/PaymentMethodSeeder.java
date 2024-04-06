package com.food_delivery.g1_a_order.persistent.seed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.food_delivery.g1_a_order.persistent.entity.PaymentMethod;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentMethodEnum;
import com.food_delivery.g1_a_order.persistent.repository.PaymentMethodRepository;


import java.util.List;

@Configuration
public class PaymentMethodSeeder {
    @Bean
    CommandLineRunner commandLinePaymentMethodRunner(PaymentMethodRepository repository) {
        return args -> {
            PaymentMethod cash = PaymentMethodEnum.COD.paymentMethod;
            List<PaymentMethod> statuses = List.of(cash);
            repository.saveAll(statuses);
        };
    }
}
