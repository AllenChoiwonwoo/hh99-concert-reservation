package com.hh99.hh5concertreservation.payments.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCommand {
    private Long userId;
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
}
