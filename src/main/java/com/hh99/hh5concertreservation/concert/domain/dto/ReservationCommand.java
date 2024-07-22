package com.hh99.hh5concertreservation.concert.domain.dto;

import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import lombok.*;

@Data
@Builder
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

    public ReservationEntity toReservationEntity() {
        return new ReservationEntity(userId, concertDescId, seatNo, price);
    }
}
