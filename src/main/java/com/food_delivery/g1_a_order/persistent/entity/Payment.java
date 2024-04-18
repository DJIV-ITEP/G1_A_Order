package com.food_delivery.g1_a_order.persistent.entity;

import com.food_delivery.g1_a_order.persistent.entity.base.BaseEntity;
import com.food_delivery.g1_a_order.persistent.enum_.PaymentStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
// @Data
@Setter
@Getter
@Builder
@Entity
@ToString
@Table(name = "payments")
public class Payment  extends BaseEntity {

    @NotNull
    private Long customerId;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "description")
    private String description;

    @NotNull
    @Builder.Default
    @ManyToOne
    private PaymentStatus paymentStatus = PaymentStatusEnum.PENDING.status;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false, referencedColumnName = "id")
    private PaymentMethod paymentMethod;

}
