package com.hh99.hh5concertreservation.payments.application.dto;

import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult {
    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private Integer status;
    private Long amount;
    public PaymentResult(PaymentEntity payment) {
        this.paymentId = payment.getId();
        this.userId = payment.getUserId();
        this.reservationId = payment.getReservationId();
        this.status = payment.getStatus();
        this.amount = payment.getAmount();

    }
}
