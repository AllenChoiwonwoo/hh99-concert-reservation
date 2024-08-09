package com.hh99.hh5concertreservation.waiting.infra;

import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenRepositoryImplWithRedisTest {

    @Autowired
    private TokenRepositoryImplWithRedis  repo;

    @DisplayName("redis 를 통해서 토큰값을 잘 가져오는지 확인")
    @Test
    void success_findByToken() {
//        String tokenStr = "9d5a273e-0fb6-3ebc-a825-009ee2363e2c";
//        Optional<String> token = repo.findEnteredTokens(tokenStr);
//        System.out.println(token.get());
    }
    @DisplayName("redis 에 토큰 값을 없을때 실패 예외처리 확인")
    @Test
    void fail_findByToken() {
//        String tokenStr = "9d5a273e-0fb6-3ebc-a825-009ee2363e2c";
//        Optional<String> token = repo.findByToken(tokenStr);
//        assert false == token.isPresent();
    }

    @DisplayName("success : redis 범위 조회 테스트")
    @Test
    void findWaitings() {
        //given
        repo.addToWaitList("token1");
        repo.addToWaitList("token2");
        repo.addToWaitList("token3");
        repo.addToWaitList("token4");
        repo.addToWaitList("token5");
        repo.addToWaitList("token6");
        repo.addToWaitList("token7");
        repo.addToWaitList("token8");
        repo.addToWaitList("token9");
        repo.addToWaitList("token10");
        repo.addToWaitList("token11");
        repo.addToWaitList("token12");

        List<String> waitings = repo.findWaitings(10);
        assert 10 == waitings.size();
    }
}