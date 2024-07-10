package com.hh99.hh5concertreservation.waiting.presentation.dto;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaitingOrderResponse {
    private Integer status;
    private Long waitingCount;

    public WaitingOrderResponse(CheckStateResult checkStateResult) {
        this.status = checkStateResult.getStatus();
        this.waitingCount = checkStateResult.getWaitingCount();
    }
}
