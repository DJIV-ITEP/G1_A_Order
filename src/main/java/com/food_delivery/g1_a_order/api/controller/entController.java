package com.food_delivery.g1_a_order.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_delivery.g1_a_order.persistent.entity.ent;
import com.food_delivery.g1_a_order.service.entService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping ("api/v1/ent")
public class entController {

    private final entService service;

    @GetMapping
    public List<ent> getAll() {
        return service.getallEnt();
    }
    

}
