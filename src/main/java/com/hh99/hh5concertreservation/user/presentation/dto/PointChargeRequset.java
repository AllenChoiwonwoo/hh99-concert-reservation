package com.hh99.hh5concertreservation.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointChargeRequset {
    private Long userId;
    private Integer point;
}
