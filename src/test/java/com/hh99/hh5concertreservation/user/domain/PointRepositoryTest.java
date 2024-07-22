package com.hh99.hh5concertreservation.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointRepositoryTest {

    @Autowired
    private PointRepository pointRepository;

    @Test
    void save() {
        // when
        UserPointEntity save = pointRepository.save(new UserPointEntity(1L, 1000L));
        // then
        assert 1L == save.getUserId();
        assert 1000L == save.getBalance();
    }

    @Test
    void findByUserId() {
        //given
        UserPointEntity save = pointRepository.save(new UserPointEntity(1L, 1000L));

        // when
        Optional<UserPointEntity> userPointEntity = pointRepository.findByUserId(1L);
        // then
        assert userPointEntity.isPresent();
        // then
        assert 1L == userPointEntity.get().getUserId();
        assert 1000L == userPointEntity.get().getBalance();
    }
}