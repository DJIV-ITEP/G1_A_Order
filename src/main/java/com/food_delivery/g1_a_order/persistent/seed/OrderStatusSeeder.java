package com.food_delivery.g1_a_order.persistent.seed;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.food_delivery.g1_a_order.persistent.entity.OrderStatus;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import com.food_delivery.g1_a_order.persistent.repository.OrderStatusRepository;

@Configuration
public class OrderStatusSeeder {

    

    @Bean
    CommandLineRunner commandLineRunner(OrderStatusRepository repository) {
        return args -> {

            OrderStatus pending = new OrderStatus(
                    OrderStatusEnum.PENDING.status);

            OrderStatus inProgress = new OrderStatus(
                OrderStatusEnum.IN_PEOGRESS.status);

            OrderStatus delivered = new OrderStatus(
                OrderStatusEnum.DELIVERED.status);

            List<OrderStatus> statuses = List.of(pending, inProgress, delivered);

            repository.saveAll(statuses);

        };
    }

}
