package com.food_delivery.g1_a_order.persistent.entity;

import com.food_delivery.g1_a_order.persistent.entity.base.BaseEntity;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@ToString
@Table(name = "orders")
public class Order extends BaseEntity {

    @NotNull
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = true, referencedColumnName = "id")
    private Address address;

    @NotNull
    private Long restaurantId;

    private Long restaurantAddressId;

    private Long deliveryId;

    @Transient
    @Builder.Default
    private float totalPrice = 0;

    @NotNull
    @Builder.Default
    @ManyToOne
    private OrderStatus orderStatus = OrderStatusEnum.CART.status;

    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        this.updatedAt = LocalDateTime.now();

    }

}
