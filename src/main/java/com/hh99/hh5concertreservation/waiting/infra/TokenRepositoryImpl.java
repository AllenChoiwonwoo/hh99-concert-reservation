package com.hh99.hh5concertreservation.waiting.infra;

import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//@Component
//@RequiredArgsConstructor
//public class TokenRepositoryImpl implements ITokenRepository {
//    private final TokenJpaRepository repo;
//    /**
//     * @param newToken
//     * @return
//     */
//    @Override
//    public TokenEntity addToWaitList(TokenEntity newToken) {
//        return repo.save(newToken);
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public Long findLastEnteredTokenId() {
//        return null;
//    }
//
//    /**
//     * @param tokenStr
//     * @return
//     */
//    @Override
//    public Optional<String> findByToken(String tokenStr) {
//
//        Optional<TokenEntity> token = repo.findByToken(tokenStr);
//        return Optional.of(token.get().getToken());
//    }
//
//    /**
//     * @param tokenId
//     * @return
//     */
//    @Override
//    public Optional<TokenEntity> findByUserId(Long tokenId) {
//        return repo.findById(tokenId);
//    }
//
//    /**
//     * @param token
//     */
//    @Override
//    public void save(TokenEntity token) {
//        repo.save(token);
//    }
//
//    /**
//     * @param wait
//     * @return
//     */
//    @Override
//    public List<TokenEntity> findTokensByStatus(Integer wait) {
//        return repo.findAllByStatus(wait);
//    }
//
//    /**
//     * @param entities
//     */
//    @Override
//    public void saveAll(List<TokenEntity> entities) {
//        repo.saveAll(entities);
//    }
//
//    /**
//     * @param token
//     * @return
//     */
//    @Override
//    public Integer findTurnNumber(String token) {
//        return null;
//    }
//
//    /**
//     * @param token
//     */
//    @Override
//    public void expireFromActives(String token) {
//
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public Set<String> findEnteredTokens() {
//        return null;
//    }
//
//    /**
//     * @param token
//     */
//    @Override
//    public void enter(String token) {
//
//    }
//
//    /**
//     * @param amount
//     * @return
//     */
//    @Override
//    public List<String> findWaitings(int amount) {
//        return null;
//    }
//
//    /**
//     * @param token
//     */
//    @Override
//    public void addToActive(String token) {
//
//    }
//
//    /**
//     * @param tokenStr
//     */
//    @Override
//    public void removeFromWait(String tokenStr) {
//
//    }
//}
