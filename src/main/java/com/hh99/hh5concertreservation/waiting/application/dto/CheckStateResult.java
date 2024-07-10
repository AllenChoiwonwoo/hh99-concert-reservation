package com.hh99.hh5concertreservation.waiting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CheckStateResult {
    private Integer status;
    private Long waitingCount;

    public CheckStateResult(Long waitingNumber, Long lastEnteredWaitingNumber) {
        this.status = 0; // 대기중
        this.waitingCount = waitingNumber - lastEnteredWaitingNumber;
    }
}
