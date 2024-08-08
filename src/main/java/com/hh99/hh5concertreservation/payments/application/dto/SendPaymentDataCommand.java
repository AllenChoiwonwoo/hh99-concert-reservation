package com.hh99.hh5concertreservation.payments.application.dto;

import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteEvent;
import lombok.Data;

@Data
public class SendPaymentDataCommand {
    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private Long amount;
    private Integer status;
    public SendPaymentDataCommand(PaymentCompleteEvent event) {
        this.paymentId = event.getPaymentId();
        this.userId = event.getUserId();
        this.reservationId = event.getReservationId();
        this.amount = event.getAmount();
        this.status = event.getStatus();
    }

    public PaymentEntity toPayment() {
        return PaymentEntity.builder()
                .userId(userId)
                .reservationId(reservationId)
                .status(status)
                .amount(amount)
                .build();
    }
}
