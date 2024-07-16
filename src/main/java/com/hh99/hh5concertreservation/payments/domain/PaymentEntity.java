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
    @Column(name = "status")
    private Integer status;
    @Column(name = "amount")
    private Long amount;
}
