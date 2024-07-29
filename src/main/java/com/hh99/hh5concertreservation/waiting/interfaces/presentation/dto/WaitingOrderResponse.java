package com.hh99.hh5concertreservation.waiting.interfaces.presentation.dto;

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
    private Integer waitingCount;

    public WaitingOrderResponse(CheckStateResult checkStateResult) {
        this.status = checkStateResult.getStatus();
        this.waitingCount = checkStateResult.getWaitingCount();
    }
}
