package com.hh99.hh5concertreservation.waiting.presentation.dto;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WaitingOrderRequest {
    private Long userId;
    private Long waitingNumber;
    private String token;
    private Integer status;

    public CheckStateCommand toCommand() {
        return CheckStateCommand.builder()
                .userId(this.userId)
                .waitingNumber(this.waitingNumber)
                .token(this.token)
                .status(this.status)
                .requestAt(System.currentTimeMillis())
                .build();
    }
}
