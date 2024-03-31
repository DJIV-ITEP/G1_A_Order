package com.food_delivery.g1_a_order.persistent.repository;

import com.food_delivery.g1_a_order.persistent.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>{

    Optional<List<Address>> findByCustomerId(Long customerId);
    Optional<Address> findFirstByCustomerIdAndLatitudeAndLongitude(Long customerId, Double latitude, Double longitude);
}
