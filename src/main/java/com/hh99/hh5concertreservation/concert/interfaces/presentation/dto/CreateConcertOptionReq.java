package com.hh99.hh5concertreservation.concert.interfaces.presentation.dto;

import com.hh99.hh5concertreservation.concert.interfaces.presentation.ConcertOptionCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Data
public class CreateConcertOptionReq {

    private Long concertId;
    private String reservationOpenAt;
    private String schedule;
    private Integer price;
    private Integer ticketAmount;

    public ConcertOptionCommand toCommand() {
        ConcertOptionCommand command = new ConcertOptionCommand();
        command.setConcertId(concertId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(reservationOpenAt, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        command.setReservationOpenAt(instant.toEpochMilli());
        command.setSchedule(schedule);
        command.setPrice(price);
        command.setTicketAmount(ticketAmount);
        return command;
    }
}
