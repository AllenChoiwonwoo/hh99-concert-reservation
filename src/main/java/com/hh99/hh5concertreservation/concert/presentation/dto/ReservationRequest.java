package com.hh99.hh5concertreservation.concert.presentation.dto;

import com.hh99.hh5concertreservation.concert.domain.dto.ReservationCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private Long userId;
    private Long concertId;
    private Long concertDescId;
    private Integer seatNo;

    public ReservationCommand toCommand() {
        return new ReservationCommand(userId, concertId, concertDescId, seatNo);
    }
}
