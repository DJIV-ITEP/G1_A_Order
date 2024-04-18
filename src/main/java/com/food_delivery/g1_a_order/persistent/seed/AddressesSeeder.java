package com.food_delivery.g1_a_order.persistent.seed;

import com.food_delivery.g1_a_order.persistent.entity.Address;

import com.food_delivery.g1_a_order.persistent.repository.AddressRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AddressesSeeder {

    @Bean
    CommandLineRunner initDatabase(AddressRepository repository) {
        return args -> {
            repository.save(new Address( 1L, 13.9668, 44.1831, "Main St, Ibb City, Yemen"));
            repository.save(new Address( 1L, 15.3694, 44.1910, "Central St, Sana'a, Yemen"));
            repository.save(new Address( 1L, 12.7855, 45.0187, "Market St, Aden, Yemen"));
            repository.save(new Address( 1L, 14.7981, 42.9545, "Coastal Rd, Hudaydah, Yemen"));
            repository.save(new Address( 1L, 13.6123, 46.1062, "Hill St, Mukalla, Yemen"));
            // Create seed data for Address entity for customerId 2
            repository.save(new Address( 2L, 15.5527, 48.5164, "Desert St, Hadhramaut, Yemen"));
            repository.save(new Address( 2L, 15.4701, 45.3226, "Mountain Rd, Taiz, Yemen"));
            repository.save(new Address( 2L, 14.0994, 46.5798, "River St, Marib, Yemen"));
            repository.save(new Address( 2L, 13.3154, 43.2473, "Beach Rd, Al Hudaydah, Yemen"));
            repository.save(new Address( 2L, 16.9398, 43.8498, "Valley St, Sa'dah, Yemen"));
            // Add more seed data as needed
            repository.save(new Address( 0L, 13.9668, 44.1831, "Main St, Ibb City, Yemen"));
            repository.save(new Address( 0L, 15.3694, 44.1910, "Central St, Sana'a, Yemen"));
            repository.save(new Address( 0L, 12.7855, 45.0187, "Market St, Aden, Yemen"));
            repository.save(new Address( 0L, 14.7981, 42.9545, "Coastal Rd, Hudaydah, Yemen"));
            repository.save(new Address( 0L, 13.6123, 46.1062, "Hill St, Mukalla, Yemen"));
           
        };
    }

}
