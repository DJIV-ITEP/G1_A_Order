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

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = true,referencedColumnName = "id")
    private Address address;

    @NotNull
    private Long restaurantId;

    private Long restaurantAddressId;

    private Long deliveryId;

    @Transient
    @Builder.Default
    private float totalPrice=0;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    @NotNull
    @Builder.Default
    @ManyToOne
    private OrderStatus orderStatus = OrderStatusEnum.CART.status;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = true,referencedColumnName = "id")
    private Payment payment;
    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    public float getTotalPrice() {
        float total = 0;
        for (OrderItem item : getOrderItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

}
