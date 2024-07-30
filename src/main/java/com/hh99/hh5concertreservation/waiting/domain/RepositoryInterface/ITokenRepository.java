package com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface;

import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository {
    TokenEntity add(TokenEntity newToken);

    Long findLastEnteredTokenId();

    Optional<TokenEntity> findByToken(String tokenStr);

    Optional<TokenEntity> findByUserId(Long tokenId);

    void save(TokenEntity token);

    List<TokenEntity> findTokensByStatus(Integer wait);

    void saveAll(List<TokenEntity> entities);
}
