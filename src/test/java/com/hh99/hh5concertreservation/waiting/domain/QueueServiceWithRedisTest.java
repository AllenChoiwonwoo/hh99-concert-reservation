package com.hh99.hh5concertreservation.waiting.domain;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueueServiceWithRedisTest {

    @Autowired
    private QueueService queueService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ITokenRepository tokenRepository;



    @DisplayName("Redis 에 정상적으로 들어가는지 확인")
    @Test
    void add() {
        String add = queueService.add(19L);
        queueService.add(20L);
        queueService.add(21L);
        queueService.add(22L);



        System.out.println(add);
    }

    @DisplayName("success : 입장 상태")
    @Test
    void checkState() {
        //given
        CheckStateCommand command = CheckStateCommand.builder()
                .userId(19L)
                .token("ffe51d3e-7d82-3723-b588-704eeddc6ab2")
                .status(0)
                .build();
        tokenRepository.addToActive(command.getToken());
        //when
        CheckStateResult checkStateResult = queueService.checkEnterState(command);
        //then
        assert 1 == checkStateResult.getStatus();
    }

    @DisplayName("fail : 입장 상태")
    @Test
    void fail_checkState() {
        //given
        CheckStateCommand command = CheckStateCommand.builder()
                .userId(19L)
                .token("ffe51d3e-7d82-3723-b588-704eeddc6ab2")
                .status(0)
                .build();
        tokenRepository.expireFromActives(command.getToken());
        tokenRepository.addToWaitList(command.getToken());
        //when
        CheckStateResult checkStateResult = queueService.checkEnterState(command);
        //then
        assert 0 == checkStateResult.getStatus();
        assert 0 < checkStateResult.getWaitingCount();
    }


    @DisplayName("success : 입장을 한 토큰인지 확인")
    @Test
    void isValidate() {
        boolean validate = queueService.isValidate("ffe51d3e-7d82-3723-b588-704eeddc6ab2");
        assert false == validate;
    }

    @DisplayName("success : 입장 테스트 ")
    @Test
    void addToActiveTest() {
        queueService.addToActive("ffe51d3e-7d82-3723-b588-704eeddc6ab2");
    }

    @DisplayName("success : 입장 한 그 토큰 만료(제거) ")
    @Test
    void expireTokenFromActive() {
        queueService.expireTokenFromActive("ffe51d3e-7d82-3723-b588-704eeddc6ab2");
    }

    @DisplayName("success : 대기열의 토큰 입장시키기")
    @Test
    void moveWaitingToEnter() {
        queueService.moveWaitingToEnter();
    }

    @DisplayName("success : 입장한 토큰중 만료시간이 지난 토큰 제거 ")
    @Test
    void expireInactiveEnterToken() {
        queueService.expireInactiveEnterToken();
    }
}