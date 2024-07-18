package com.hh99.hh5concertreservation.payments.presentation.dto;

import com.hh99.hh5concertreservation.payments.application.dto.PaymentResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private Integer status;
    private Long amount;
    public PaymentResponse(PaymentResult result) {
        this.paymentId = result.getPaymentId();
        this.userId = result.getUserId();
        this.reservationId = result.getReservationId();
        this.status = HttpStatusCode.valueOf(result.getStatus()).value();
        this.amount = result.getAmount();
    }
}
