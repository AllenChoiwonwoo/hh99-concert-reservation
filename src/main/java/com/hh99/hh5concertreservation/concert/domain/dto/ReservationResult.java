package com.hh99.hh5concertreservation.concert.domain.dto;

import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResult {
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    private Integer reservationState;

    public ReservationResult(Long concertId, ReservationEntity saved) {
        this.concertId = concertId;
        this.concertDescId = saved.getConcertOptionId();
        this.seatNo = saved.getSeatNo();
        this.reservationState = saved.getStatus();
    }
}
