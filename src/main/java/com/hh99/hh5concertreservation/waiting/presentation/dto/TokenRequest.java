package com.hh99.hh5concertreservation.waiting.presentation.dto;

import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
    private Long userId;

    public CreateTokenCommand toCommand() {
        return CreateTokenCommand.builder()
                .userId(this.userId)
                .requestAt(System.currentTimeMillis())
                .build();
    }
}
