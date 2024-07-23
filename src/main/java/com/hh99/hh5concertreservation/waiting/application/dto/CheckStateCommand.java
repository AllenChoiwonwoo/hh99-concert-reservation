package com.hh99.hh5concertreservation.waiting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CheckStateCommand {
    private Long userId;
    private Long waitingNumber;
    private String token;
    private Integer status;
    private Long requestAt;
}
