package com.hh99.hh5concertreservation.concert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "concert")
@Entity
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "concert", fetch = FetchType.EAGER)
    private List<ConcertOption> concertDescs;

    public ConcertEntity(String name) {
        this.name = name;
    }
}
