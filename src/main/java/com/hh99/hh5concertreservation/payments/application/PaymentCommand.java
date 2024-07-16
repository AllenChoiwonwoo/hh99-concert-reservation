package com.hh99.hh5concertreservation.payments.application;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentCommand {
    private Long userId;
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;

    public PaymentCommand(Long userId, Long concertId, Long concertDescId, Integer seatNo) {
        this.userId = userId;
        this.concertId = concertId;
        this.concertDescId = concertDescId;
        this.seatNo = seatNo;
    }
}
