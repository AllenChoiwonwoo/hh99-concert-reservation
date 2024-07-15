package com.hh99.hh5concertreservation.concert.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResult {
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    private Integer reservationState;
}
