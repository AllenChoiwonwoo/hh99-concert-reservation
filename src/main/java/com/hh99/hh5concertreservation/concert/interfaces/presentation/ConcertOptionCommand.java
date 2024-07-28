package com.hh99.hh5concertreservation.concert.interfaces.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ConcertOptionCommand {
    private Long concertId;
    private Long reservationOpenAt;
    private String schedule;
    private Integer price;
    private Integer ticketAmount;
}
