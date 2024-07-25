package com.hh99.hh5concertreservation.concert.domain.dto;

import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.hh99.hh5concertreservation.concert.domain.ConcertService.logTransactionStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResult {
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    private Integer reservationState;

    public ReservationResult(Long concertId, ReservationEntity saved) {
        logTransactionStatus( "6 ReservationResult. create result");
        this.concertId = concertId;
        this.concertDescId = saved.getConcertOptionId();
        this.seatNo = saved.getSeatNo();
        this.reservationState = saved.getStatus();
    }
}
