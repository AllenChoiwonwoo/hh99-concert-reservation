package com.hh99.hh5concertreservation.waiting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CheckStateResult {
    private Integer status; // 0 기달, 1 입장
    private Integer waitingCount;

}
