package com.hh99.hh5concertreservation.waiting.domain;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
class QueueServiceWithRedisTest {

    @Autowired
    private QueueService queueService;
    @Autowired
    private RedissonClient redissonClient;



    @DisplayName("Redis 에 정상적으로 들어가는지 확인")
    @Test
    void add() {
        TokenEntity add = queueService.add(19L);
        queueService.add(20L);
        queueService.add(21L);
        queueService.add(22L);



        System.out.println(add);
    }

    @Test
    void checkState() {
        CheckStateCommand command = CheckStateCommand.builder()
                .userId(19L)
                .token("ffe51d3e-7d82-3723-b588-704eeddc6ab2")
                .status(0)
                .build();
        CheckStateResult checkStateResult = queueService.checkState(command);
    }

    @Test
    void isValidate() {
        boolean validate = queueService.isValidate("ffe51d3e-7d82-3723-b588-704eeddc6ab2");
        assert false == validate;
    }

    @Test
    void addToActiveTest() {
        queueService.addToActive("ffe51d3e-7d82-3723-b588-704eeddc6ab2");
    }

    @Test
    void expireTokenFromActive() {
        queueService.expireTokenFromActive("ffe51d3e-7d82-3723-b588-704eeddc6ab2");
    }

    @Test
    void moveWaitingToEnter() {
        queueService.moveWaitingToEnter();
//        RScoredSortedSet<String> waitQueue = redissonClient.getScoredSortedSet("waitQueue");
//        Collection<String> strings = waitQueue.valueRange(0, 2);
//        strings.forEach(System.out::println);
    }

    @Test
    void expireInactiveEnterToken() {
        queueService.expireInactiveEnterToken();
    }
}