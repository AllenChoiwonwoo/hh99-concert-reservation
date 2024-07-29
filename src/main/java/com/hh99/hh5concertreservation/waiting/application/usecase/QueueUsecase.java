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
        String token = queueService.add(command.getUserId());
        return new CreateTokenResult(command.getUserId(), token);
    }

    public CheckStateResult checkWaitingStatus(CheckStateCommand command) {
        CheckStateResult checkStateResult = queueService.checkEnterState(command);
        return checkStateResult;
    }

    public boolean validateToken(String token) {
        return queueService.isValidate(token);
    }
}
