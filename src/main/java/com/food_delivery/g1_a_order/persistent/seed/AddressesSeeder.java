package com.food_delivery.g1_a_order.persistent.seed;

import com.food_delivery.g1_a_order.persistent.entity.Address;

import com.food_delivery.g1_a_order.persistent.repository.AddressRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class AddressesSeeder {

    @Bean
    CommandLineRunner initDatabase(AddressRepository repository) {
        return args -> {
            repository.save(new Address(1L, 1L, 13.9668, 44.1831, "Main St, Ibb City, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(2L, 1L, 15.3694, 44.1910, "Central St, Sana'a, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(3L, 1L, 12.7855, 45.0187, "Market St, Aden, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(4L, 1L, 14.7981, 42.9545, "Coastal Rd, Hudaydah, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(5L, 1L, 13.6123, 46.1062, "Hill St, Mukalla, Yemen", LocalDateTime.now(), null));
            // Create seed data for Address entity for customerId 2
            repository.save(new Address(6L, 2L, 15.5527, 48.5164, "Desert St, Hadhramaut, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(7L, 2L, 15.4701, 45.3226, "Mountain Rd, Taiz, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(8L, 2L, 14.0994, 46.5798, "River St, Marib, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(9L, 2L, 13.3154, 43.2473, "Beach Rd, Al Hudaydah, Yemen", LocalDateTime.now(), null));
            repository.save(new Address(10L, 2L, 16.9398, 43.8498, "Valley St, Sa'dah, Yemen", LocalDateTime.now(), null));
            // Add more seed data as needed
        };
    }

}
