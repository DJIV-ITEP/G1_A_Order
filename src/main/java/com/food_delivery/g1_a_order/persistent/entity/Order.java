package com.food_delivery.g1_a_order.persistent.entity;

import com.food_delivery.g1_a_order.persistent.entity.base.BaseEntity;
import com.food_delivery.g1_a_order.persistent.enum_.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private float totalPrice;

    @NotNull
    @Builder.Default
    @ManyToOne
    private OrderStatus orderStatus = OrderStatusEnum.CART.status;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = true,referencedColumnName = "id")
    private Payment payment;
    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>() ;

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        this.updatedAt = LocalDateTime.now();

    }

    public float getTotalPrice() {
        for (OrderItem item : getOrderItems()) {
            this.totalPrice += item.getPrice() * item.getQuantity();
        }
        return this.totalPrice;
    }

}
