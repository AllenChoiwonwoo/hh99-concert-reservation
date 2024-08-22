package com.hh99.hh5concertreservation.waiting.infra;

import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
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
    private static final String WATI_QUEUE_KEY = "waitQueue";
    private static final String ENTER_QUEUE_KEY = "enterQueue";


    /**
     * @param newToken
     * @return
     */
    @Override
    public String addToWaitList(String newToken) {

        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(WATI_QUEUE_KEY);
        sortedSet.add(System.currentTimeMillis(), newToken);
        return newToken;
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
        return System.currentTimeMillis() + (10 * 60 * 1000);
    }
}
