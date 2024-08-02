package com.hh99.hh5concertreservation.waiting.domain;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @Mock
    private ITokenRepository tokenRepository;
    @InjectMocks
    private QueueService queueService;
    Long userId = 10L;
    Long waitingNumber = 100L;
    String tokenStr = "68b329da-9893-3340-99c7-d8ad5cb9c940";


    @DisplayName("success : 대기열에 추가 성공") // 이 테스트는 별 의미없어보인다.
    @Test
    void add() {
        //given
//        TokenEntity inputToken = new TokenEntity(userId, 0);
        TokenEntity savedToken = new TokenEntity(waitingNumber, tokenStr, userId, 0, 1720594902702L);

        // FIXME : 인자값으로 any() 를 쓰는게 맞는가? 안쓰면
        given(tokenRepository.addToWaitList(any())).willReturn(tokenStr);
        //when
        String result = queueService.addWaitingQueue(userId);
        //then
        assert Objects.nonNull(result);
    }

    @DisplayName("success : 대기순서 확인 - 입장가능")
    @Test
    void testCheckState1() {
        TokenEntity token = new TokenEntity(waitingNumber, tokenStr, userId, 1, System.currentTimeMillis() + 60000);
        CheckStateCommand command = new CheckStateCommand(userId, waitingNumber, tokenStr, 0, System.currentTimeMillis() );
        given(tokenRepository.findByToken(command.getToken())).willReturn(Optional.of(token.getToken()));
        //when
        CheckStateResult result = queueService.checkEnterState(command);
        //then
        assert 1 == result.getStatus();
    }

    @DisplayName("success : 대기순서 확인 - 아직 기다려야한다.")
    @Test
    void testCheckState2() {
        CheckStateCommand command = new CheckStateCommand(userId, waitingNumber, tokenStr, 0, System.currentTimeMillis() );

        given(tokenRepository.findByToken(command.getToken())).willReturn(Optional.empty());
        given(tokenRepository.findTurnNumber(command.getToken())).willReturn(11);
        //when
        CheckStateResult result = queueService.checkEnterState(command);
        //then
        assert 0 == result.getStatus();
        assert 11 == result.getWaitingCount();
    }
}