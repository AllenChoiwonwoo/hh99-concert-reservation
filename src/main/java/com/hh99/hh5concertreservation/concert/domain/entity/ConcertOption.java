package com.hh99.hh5concertreservation.concert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "concert_option")
@Entity
public class ConcertOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ConcertEntity concert;

    @Column(name = "reservation_open_at")
    private Long reservationOpenAt;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "price")
    private Integer price;
    @Column(name = "ticket_amount")
    private Integer ticketAmount;
}
