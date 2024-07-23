package com.hh99.hh5concertreservation.concert.interfaces.presentation.dto;

import com.hh99.hh5concertreservation.concert.domain.dto.SeatsInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ReftSeatsResponse {
    private Long concertDescId;
    private Map<Integer, Integer> seatStates;

    public ReftSeatsResponse(Long concertDescId, Map<Integer, Integer> seatStates) {
        this.concertDescId = concertDescId;
        this.seatStates = seatStates;
    }
}
