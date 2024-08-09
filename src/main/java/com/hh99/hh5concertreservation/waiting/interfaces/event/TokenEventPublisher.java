package com.hh99.hh5concertreservation.waiting.interfaces.event;

import com.hh99.hh5concertreservation.waiting.application.usecase.QueueUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
@Component
@RequiredArgsConstructor
class TokenEventListener {
    private final QueueUsecase queueUsecase;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void expireToken(String token) {
        queueUsecase.expireToken(token);
    }
}
