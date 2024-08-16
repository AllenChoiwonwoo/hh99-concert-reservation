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
    @Column(name = "del_yn")
    private Character delYn = 'N';

    public PaymentCompleteEventEntity(Long paymentId, String message, Integer eventState) {
        this.status = eventState;
        this.paymentId = paymentId;
        this.createdAt = System.currentTimeMillis();
        this.message = message;
    }

    public static PaymentCompleteEventEntity createInit(Long paymentId, String message) {
        return PaymentCompleteEventEntity.builder()
                .status(0) // init
                .paymentId(paymentId)
                .createdAt(System.currentTimeMillis())
                .message(message)
                .build();
    }

    public PaymentCompleteEventEntity published() {
        this.status = 1;
        return this;
    }

    public PaymentCompleteEventEntity setDelete() {
        this.setDelYn('Y');
        return this;
    }
}
