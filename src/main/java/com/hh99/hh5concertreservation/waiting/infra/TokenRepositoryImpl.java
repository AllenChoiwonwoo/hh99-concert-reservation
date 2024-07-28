package com.hh99.hh5concertreservation.waiting.infra;

import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRepositoryImpl implements ITokenRepository {
    private final TokenJpaRepository repo;
    /**
     * @param newToken
     * @return
     */
    @Override
    public TokenEntity add(TokenEntity newToken) {
        return repo.save(newToken);
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
        return repo.findByToken(tokenStr);
    }

    /**
     * @param tokenId
     * @return
     */
    @Override
    public Optional<TokenEntity> findByUserId(Long tokenId) {
        return repo.findById(tokenId);
    }

    /**
     * @param token
     */
    @Override
    public void save(TokenEntity token) {
        repo.save(token);
    }

    /**
     * @param wait
     * @return
     */
    @Override
    public List<TokenEntity> findTokensByStatus(Integer wait) {
        return repo.findAllByStatus(wait);
    }

    /**
     * @param entities
     */
    @Override
    public void saveAll(List<TokenEntity> entities) {
        repo.saveAll(entities);
    }
}
