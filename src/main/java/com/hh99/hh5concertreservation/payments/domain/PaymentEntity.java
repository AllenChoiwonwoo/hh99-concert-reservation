package com.hh99.hh5concertreservation.payments.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "reservation_id")
    private Long reservationId;
    @Column(name = "status") // 오디너리,
    private Integer status;
    @Column(name = "amount")
    private Long amount;

    public static PaymentEntity createActivePaymanet(Long userId, Long id, Long price) {
        return PaymentEntity.builder()
                .userId(userId)
                .reservationId(id)
                .status(2)
                .amount(price)
                .build();
    }
}
