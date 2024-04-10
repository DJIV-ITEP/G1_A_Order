package com.food_delivery.g1_a_order.persistent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.food_delivery.g1_a_order.persistent.entity.base.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
@Entity
@Table(name = "order_status")
public class OrderStatus extends BaseEntity {

    @Column(nullable = false,name = "status_value")
    private String value;

    @Builder.Default
    @Column(nullable = false)
    private int sequence=0;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderStatus")
    private List<Order> orders;
}
