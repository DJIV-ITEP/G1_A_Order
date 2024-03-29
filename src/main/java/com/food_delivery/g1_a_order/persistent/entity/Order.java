package com.food_delivery.g1_a_order.persistent.entity;

import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
// @Data
@Setter
@Getter
@Builder
@Entity
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private Long customerId;

    private Long customerAddressId;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false,referencedColumnName = "id")
    private Address address;

    @NotNull
    private Long restaurantId;

    private Long restaurantAddressId;

    private Long deliveryId;

    @Transient
    private float totalPrice;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    @NotNull
    @Builder.Default
    @ManyToOne
    private OrderStatus orderStatus = OrderStatusEnum.CART.status;

    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

}
