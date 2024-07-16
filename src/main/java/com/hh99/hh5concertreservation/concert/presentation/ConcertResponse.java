package com.hh99.hh5concertreservation.concert.presentation;

import com.hh99.hh5concertreservation.concert.domain.entity.ConcertEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertResponse {
    private Long concertId;
    private String name;

    public ConcertResponse(ConcertEntity entity) {
        this.concertId = entity.getId();
        this.name = entity.getName();
    }
}
