package com.food_delivery.g1_a_order.persistent.seed;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.food_delivery.g1_a_order.persistent.entity.ent;
import com.food_delivery.g1_a_order.persistent.repository.entRepository;

@Configuration
public class entSeed {

    @Bean
    CommandLineRunner commandLineRunner(entRepository repository) {
        return args -> {

            ent e1 = new ent(
                    1l,
                    "e1",
                    LocalDate.of(1990, Month.APRIL, 3)
            );
                    
            ent e2 = new ent(
                2l,
                "e2",
                LocalDate.of(1889, Month.DECEMBER, 5)
            );
                
            repository.saveAll(
                List.of(e1,e2)
            );

        };
    }

}
