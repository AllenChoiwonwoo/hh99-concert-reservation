package com.hh99.hh5concertreservation.concert.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {
    private Long userId;
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    private Long paymentId;
    private Integer paymentState;
}
