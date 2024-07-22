package com.hh99.hh5concertreservation.waiting.infra;

import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements ITokenRepository {
    /**
     * @param newToken
     * @return
     */
    @Override
    public TokenEntity add(TokenEntity newToken) {
        return null;
    }

    /**
     * @param token
     * @return
     */
    @Override
    public TokenEntity findToken(String token) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Long findLastEnteredTokenId() {
        return null;
    }

    /**
     * @param tokenStr
     * @return
     */
    @Override
    public Optional<TokenEntity> findByToken(String tokenStr) {
        return Optional.empty();
    }

    /**
     * @param tokenId
     * @return
     */
    @Override
    public TokenEntity findByUserId(Long tokenId) {
        return null;
    }

    /**
     * @param token
     */
    @Override
    public void save(TokenEntity token) {

    }

    /**
     * @param currentTimeMillis
     */
    @Override
    public void expireInactiveToken(long currentTimeMillis) {

    }
}
