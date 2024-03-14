package com.food_delivery.g1_a_order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food_delivery.g1_a_order.persistent.entity.ent;
import com.food_delivery.g1_a_order.persistent.repository.entRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class entService {

    private final entRepository repository;

    public List<ent> getallEnt()
    {
        return repository.findAll();
    }


}
