package com.hh99.hh5concertreservation.payments.domain.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import com.hh99.hh5concertreservation.payments.infra.PaymentCompleteEventEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentCompleteEvent {

    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private Long amount;
    private Integer status;
    private String token;

    public PaymentCompleteEvent(PaymentEntity payment, String token) {
        this.paymentId = payment.getId();
        this.userId = payment.getUserId();
        this.reservationId = payment.getReservationId();
        this.amount = payment.getAmount();
        this.status = payment.getStatus();
        this.token = token;
    }

    public PaymentCompleteEvent(PaymentCompleteEventEntity entity, ObjectMapper mapper) {
        try {
            PaymentCompleteEvent paymentCompleteEvent = mapper.readValue(entity.getMessage(), this.getClass());
            this.paymentId = paymentCompleteEvent.getPaymentId();
            this.userId = paymentCompleteEvent.getUserId();
            this.reservationId = paymentCompleteEvent.getReservationId();
            this.amount = paymentCompleteEvent.getAmount();
            this.status = paymentCompleteEvent.getStatus();
            this.token = paymentCompleteEvent.getToken();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
