package com.food_delivery.g1_a_order.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrderRestaurantService {

    private final WebClient restaurantClient;

    public Mono<Restaurant> getRestaurantData(Long id) {

        return restaurantClient.get()
                .uri("/api/restaurants/" + id)
                .retrieve()
                .bodyToMono(Restaurant.class);
    }
}

@Setter
@Getter
@ToString
class Restaurant {
    private Data data;

    public Long getLocationId() {
        return data != null ? data.getLocation().getId() : null;
    }
}

@Setter
@Getter
@ToString
class Data {
    private Long id;
    private String name;
    private String phone_number;
    private Location location;
    private Integer is_visible;

    // getters and setters
}

@Setter
@Getter
@ToString
class Location {
    private long id;
    private String address;
    private Double latitude;
    private Double longitude;

    // getters and setters
}
