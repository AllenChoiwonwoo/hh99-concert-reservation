package com.hh99.hh5concertreservation.waiting.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TokenRepositoryImplWithRedis implements ITokenRepository {
    private final RedissonClient redissonClient;
    private final ObjectMapper mapper;

    private final TokenJpaRepository repo;
    private static final String WATI_QUEUE_KEY = "waitQueue";
    private static final String ENTER_QUEUE_KEY = "enterQueue";


    /**
     * @param newToken
     * @return
     */
    @Override
    public TokenEntity addToWaitList(TokenEntity newToken) {

        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(WATI_QUEUE_KEY);
        sortedSet.add(System.currentTimeMillis(), newToken.getToken());
        return newToken;
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
    // active 에 있는지 확인
    public Optional<String> findByToken(String tokenStr) {
        RSet<String> set = redissonClient.getSet(ENTER_QUEUE_KEY);
        boolean contains = set.contains(tokenStr);
        if (!contains) {
            return Optional.empty();
        }
        return Optional.of(tokenStr);
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

    /**
     * @param token
     * @return
     */
    @Override
    public Integer findTurnNumber(String token) {
        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(WATI_QUEUE_KEY);
        // TTL 설정을 위해 RBucket 사용
        return sortedSet.rank(token);
    }

    /**
     * @param token
     */
    @Override
    public void expireFromActives(String token) {
        RSet<String> set = redissonClient.getSet(ENTER_QUEUE_KEY);
        set.remove(token);
    }

    /**
     * @return
     */
    @Override
    public Set<String> findEnteredTokens() {
        RSet<String> set = redissonClient.getSet(ENTER_QUEUE_KEY);
        Set<String> strings = set.readAll();
        return strings;
    }

    /**
     * @param token
     */
    @Override
    public void enter(String token) {
        RSet<String> set = redissonClient.getSet(ENTER_QUEUE_KEY);
        long newExpiredAt = getNewExpiredAt();
        boolean add = set.add(token +":"+ newExpiredAt);
    }

    /**
     * @param amount
     * @return
     */
    @Override
    public List<String> findWaitings(int amount) {
        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(WATI_QUEUE_KEY);
        List<String> tokens = sortedSet.valueRange(0, amount - 1).stream().toList();
        return tokens;
    }

    /**
     * @param token
     */
    @Override
    public void addToActive(String token) {
        RSet<String> set = redissonClient.getSet(ENTER_QUEUE_KEY);
        set.add(token+":"+getNewExpiredAt());
    }

    /**
     * @param tokenStr
     */
    @Override
    public void removeFromWait(String tokenStr) {
        redissonClient.getScoredSortedSet(WATI_QUEUE_KEY).remove(tokenStr);
    }

    private long getNewExpiredAt() {
        return System.currentTimeMillis() + (5 * 60 * 1000);
    }
}
