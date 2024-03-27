package com.food_delivery.g1_a_order.persistent.entity;

import java.time.LocalDateTime;

import com.food_delivery.g1_a_order.persistent.enum_.PaymentStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
// @Data
@Setter
@Getter
@Builder
@Entity
@ToString
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private Long customerId;

    @Transient
    private float amount;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    @NotNull
    @Builder.Default
    @ManyToOne
    private PaymentStatus paymentStatus = PaymentStatusEnum.PENDING.status;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false,referencedColumnName = "id")
    private PaymentMethod paymentMethod;

}
