package com.hh99.hh5concertreservation.waiting.application.usecase;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenResult;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueUsecase {

    private final QueueService queueService;

    public CreateTokenResult addWaitlist(CreateTokenCommand command) {
        TokenEntity token = queueService.add(command.getUserId());
        return new CreateTokenResult(token);
    }

    public CheckStateResult checkWaitingStatus(CheckStateCommand command) {
        CheckStateResult checkStateResult = queueService.checkState(command);
        return checkStateResult;
    }
}
/*
데이터 베이스 만을 활용해서
지금은 성능보다는 비즈니스 로직에 대한 정확한 이해
    지금은 놀이공원보다는 , 은행창구 방식
    콘서트별 대기열은 빼자, 통 대기열로 가자

그렇다면 요구사항 분석때 로직구현이 나와야 했었는지?
    요구사항분석을 어디까지 했어야하느느지
    애매하게 디테일하게 생각을 하니까
    로직이 절차지향이 되어가는것 같다.

dip


 */
