package com.hh99.hh5concertreservation.waiting.interfaces.presentation.dto;

import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private Long userId;
    private String token;

    public TokenResponse(CreateTokenResult result) {
        this.userId = result.getUserId();
        this.token = result.getToken();
    }
}
