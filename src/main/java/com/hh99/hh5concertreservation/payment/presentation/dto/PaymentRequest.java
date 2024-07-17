package com.hh99.hh5concertreservation.payment.presentation.dto;

import com.hh99.hh5concertreservation.concert.domain.dto.PaymentCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long userId;
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    
    public PaymentCommand toCommand() {
        return new PaymentCommand(userId, concertId, concertDescId, seatNo);
    }
}
