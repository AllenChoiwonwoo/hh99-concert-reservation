package com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface;

import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITokenRepository {
    String addToWaitList(String newToken);

    Optional<String> findByToken(String tokenStr);


    Integer findTurnNumber(String token);

    void expireFromActives(String token);

    Set<String> findEnteredTokens();

    List<String> findWaitings(int amount);

    void addToActive(String token);

    void removeFromWait(String tokenStr);
}
