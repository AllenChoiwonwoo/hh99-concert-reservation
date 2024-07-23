package com.hh99.hh5concertreservation.waiting.application.dto;

import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenResult {
    private Long userId;
    private String token;

    public CreateTokenResult(TokenEntity token) {
        this.userId = token.getUserId();
        this.token = token.getToken();
    }
}
