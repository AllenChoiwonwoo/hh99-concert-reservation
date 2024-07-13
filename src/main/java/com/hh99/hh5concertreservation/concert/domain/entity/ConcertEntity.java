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
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
}
