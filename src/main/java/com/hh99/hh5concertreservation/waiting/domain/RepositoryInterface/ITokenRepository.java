package com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface;

import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ITokenRepository {
    TokenEntity add(TokenEntity newToken);

    TokenEntity findToken();

    Long findLastEnteredTokenId();

    Optional<TokenEntity> findByIdAndUserId(Long userId, String tokenStr);

    TokenEntity findById(Long tokenId);

    void save(TokenEntity token);
    
    List<TokenEntity> findExpiredToken(long currentTimeMillis);
}
