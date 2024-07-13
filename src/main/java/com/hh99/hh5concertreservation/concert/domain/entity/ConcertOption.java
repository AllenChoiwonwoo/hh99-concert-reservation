package com.hh99.hh5concertreservation.concert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class ConcertOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "concert_id")
    private Long concertId;
    @Column(name = "reservation_open_at")
    private Long reservationOpenAt;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "price")
    private Integer price;
    @Column(name = "ticket_amount")
    private Integer ticketAmount;
}
