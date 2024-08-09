package com.hh99.hh5concertreservation.waiting.application.usecase;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenResult;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueUsecase {

    private final QueueService queueService;


    /**
     * 대기열에 유저의 토큰을 넣는다.
     * @param command
     * @return CreateTokenResult
     */
    public CreateTokenResult addWaitlist(CreateTokenCommand command) {
        String token = queueService.addWaitingQueue(command.getUserId());
        return new CreateTokenResult(command.getUserId(), token);
    }

    /**
     * 대기열 상태를 조회한다.
     * @param command
     * @return CheckStateResult
     */
    public CheckStateResult checkWaitingStatus(CheckStateCommand command) {
        CheckStateResult checkStateResult = queueService.checkEnterState(command);
        return checkStateResult;
    }

    /**
     * 유저의 액티브 상태를 조회한다.
     * @param token
     * @return boolean
     */
    public boolean validateToken(String token) {
        return queueService.isValidate(token);
    }
}
