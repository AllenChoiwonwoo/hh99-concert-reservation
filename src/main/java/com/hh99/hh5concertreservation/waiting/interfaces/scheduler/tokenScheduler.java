package com.hh99.hh5concertreservation.waiting.interfaces.scheduler;

import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class tokenScheduler {
    private final QueueService queueService;
    
    
    @Scheduled(fixedDelay = 61000)
    public void expireInactiveToken() {
        queueService.expireInactiveEnterToken();
    }

    @Scheduled(fixedDelay = 60000)
    public void moveWaitingToEnter() {
        queueService.moveWaitingToEnter();
    }
}
