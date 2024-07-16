package com.hh99.hh5concertreservation.payment.presentation.dto;

import com.hh99.hh5concertreservation.concert.domain.dto.PaymentResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long userId;
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    private Long paymentId;
    private Integer paymentState;
    
    public PaymentResponse(PaymentResult result) {
        this.userId = result.getUserId();
        this.concertId = result.getConcertId();
        this.concertDescId = result.getConcertDescId();
        this.seatNo = result.getSeatNo();
        this.paymentId = result.getPaymentId();
        this.paymentState = result.getPaymentState();
    }
}
