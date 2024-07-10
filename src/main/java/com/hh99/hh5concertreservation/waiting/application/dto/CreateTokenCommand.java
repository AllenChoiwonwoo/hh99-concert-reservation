package com.hh99.hh5concertreservation.waiting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateTokenCommand {
    private Long userId;
    private Long requestAt;
}
