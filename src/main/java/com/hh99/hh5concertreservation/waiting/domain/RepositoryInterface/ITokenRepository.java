package com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface;

import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITokenRepository {
    TokenEntity addToWaitList(TokenEntity newToken);

    Long findLastEnteredTokenId();

    Optional<String> findByToken(String tokenStr);

    Optional<TokenEntity> findByUserId(Long tokenId);

    void save(TokenEntity token);

    List<TokenEntity> findTokensByStatus(Integer wait);

    void saveAll(List<TokenEntity> entities);

    Integer findTurnNumber(String token);

    void expireFromActives(String token);

    Set<String> findEnteredTokens();

    void enter(String token);

    List<String> findWaitings(int amount);

    void addToActive(String token);

    void removeFromWait(String tokenStr);
}
