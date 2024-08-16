package com.hh99.hh5concertreservation.payments.application.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public SendPaymentDataCommand(String message, ObjectMapper mapper) {
        try {
            PaymentCompleteEvent event = mapper.readValue(message, PaymentCompleteEvent.class);
            this.paymentId = event.getPaymentId();
            this.userId = event.getUserId();
            this.reservationId = event.getReservationId();
            this.amount = event.getAmount();
            this.status = event.getStatus();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
