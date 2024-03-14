package com.food_delivery.g1_a_order.persistent.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
// @NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ent {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate date;
    @Transient
    private int age;

    public ent(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    
}
