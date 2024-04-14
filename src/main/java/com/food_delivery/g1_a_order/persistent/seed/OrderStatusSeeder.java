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

            OrderStatus cart = OrderStatusEnum.CART.status;

            OrderStatus pending = OrderStatusEnum.PENDING.status;

            OrderStatus inProgress = OrderStatusEnum.IN_PEOGRESS.status;

            OrderStatus readyToPickup = OrderStatusEnum.READY_TO_PICKUP.status;

            OrderStatus onTheWay = OrderStatusEnum.ON_THE_WAY.status;

            OrderStatus delivered = OrderStatusEnum.DELIVERED.status;

            OrderStatus reject = OrderStatusEnum.REJECT.status;

            OrderStatus canceled = OrderStatusEnum.CANCELED.status;

            List<OrderStatus> statuses = List.of(cart,
                    pending,
                    inProgress,
                    readyToPickup,
                    onTheWay,
                    delivered,
                    reject,
                    canceled);

            repository.saveAll(statuses);

        };
    }

}
