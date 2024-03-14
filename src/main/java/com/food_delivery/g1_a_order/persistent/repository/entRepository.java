package com.food_delivery.g1_a_order.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food_delivery.g1_a_order.persistent.entity.ent;

@Repository
public interface entRepository extends JpaRepository<ent,Long> {

}
