package com.food_delivery.g1_a_order.persistent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import com.food_delivery.g1_a_order.persistent.entity.base.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    private Long itemId;
    private Long quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    private Order order;
}
