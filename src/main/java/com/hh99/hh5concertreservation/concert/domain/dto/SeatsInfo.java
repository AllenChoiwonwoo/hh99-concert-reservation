package com.hh99.hh5concertreservation.concert.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SeatsInfo {
    private Long concertOptionId;
    private Integer state;
    private Integer seatNo;
}
