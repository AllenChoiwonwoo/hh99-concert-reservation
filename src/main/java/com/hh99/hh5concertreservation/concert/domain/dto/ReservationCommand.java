package com.hh99.hh5concertreservation.concert.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCommand {
    private Long userId;
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;
    private Integer price;

    public ReservationCommand(Long userId, Long concertId, Long concertDescId, Integer seatNo) {
        this.userId = userId;
        this.concertId = concertId;
        this.concertDescId = concertDescId;
        this.seatNo = seatNo;
    }
}
