package com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface;

import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ITokenRepository {
    TokenEntity add(TokenEntity newToken);

    TokenEntity findToken(String token);

    Long findLastEnteredTokenId();

    Optional<TokenEntity> findByToken(String tokenStr);

    TokenEntity findByUserId(Long tokenId);

    void save(TokenEntity token);
    void expireInactiveToken(long currentTimeMillis);
}
