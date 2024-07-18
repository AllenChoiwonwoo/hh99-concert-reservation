package com.hh99.hh5concertreservation.concert.interfaces.presentation.dto;

import com.hh99.hh5concertreservation.concert.domain.dto.ReservationResult;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationResponse {
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    private Integer reservationState;
    public ReservationResponse(ReservationResult result) {
        this.concertId = result.getConcertId();
        concertDescId = result.getConcertDescId();
        seatNo = result.getSeatNo();
        reservationState = result.getReservationState();
    }
}
