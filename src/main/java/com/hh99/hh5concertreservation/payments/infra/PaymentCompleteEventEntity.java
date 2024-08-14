package com.hh99.hh5concertreservation.payments.infra;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class PaymentCompleteEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "paymentId")
    private Long paymentId;
    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "message")
    private String message;

    public PaymentCompleteEventEntity(Long paymentId, String message, Integer eventState) {
        this.status = eventState;
        this.paymentId = paymentId;
        this.createdAt = System.currentTimeMillis();
        this.message = message;
    }
}
