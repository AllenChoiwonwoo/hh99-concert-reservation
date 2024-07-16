package com.hh99.hh5concertreservation.waiting.application.usecase;

import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenResult;
import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)

class QueueUsecaseTest {
    @InjectMocks
    private QueueUsecase queueUsecase;
    @Mock
    private QueueService queueService;

    @DisplayName("success : 대기열에 추가 성공")
    @Test
    void addWaitlist() {
        //given
        CreateTokenCommand command = CreateTokenCommand.builder()
                .userId(1l).build();

        TokenEntity token = TokenEntity.builder()
                .userId(command.getUserId())
                .expiredAt(System.currentTimeMillis()+1000)
                .status(1).token("token1").build();

        given(queueService.add(token.getUserId())).willReturn(token);

        //when
        CreateTokenResult result = queueUsecase.addWaitlist(command);
        assert result.getToken().equals( token.getToken());
    }

    @DisplayName("success : 대기순번 조회 선공")
    @Test
    void checkWaitingOrder() {
//        // given
//        String tokenStr = "token1";
//        Integer status = 0;
//        Long count = 30L;
//
//        TokenEntity token = TokenEntity.builder().id(3l).token(tokenStr).status(status).build();
//        given(queueService.checkState(tokenStr)).willReturn(token);
//        given(queueService.countWaitingPeople(token.getId())).willReturn(count);
//
//        // when
//        WaitingOrderResponse waitingOrderResponse = queueUsecase.checkWaitingOrder(tokenStr);
//
//        // then
//        assert waitingOrderResponse.getStatus() == status;
    }
}
