package com.hh99.hh5concertreservation.payments.domain.event;

import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentCompleteEvent {

    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private Long amount;
    private Integer status;

    public PaymentCompleteEvent(PaymentEntity payment) {
        this.paymentId = payment.getId();
        this.userId = payment.getUserId();
        this.reservationId = payment.getReservationId();
        this.amount = payment.getAmount();
        this.status = payment.getStatus();
    }
}