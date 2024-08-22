package com.hh99.hh5concertreservation.waiting.interfaces.scheduler;

import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The type Token scheduler.
 */
@Component
@RequiredArgsConstructor
public class tokenScheduler {
    private final QueueService queueService;

    /**
     * 만료된 active 토큰을 제거한다.
     * 주기 : 1분
     */
    @Scheduled(fixedDelay = 61000)
    public void expireActiveToken() {
        queueService.expireInactiveEnterToken();
    }

    /**
     * 대기열에서 선순위 N명을 활성 상태로 변경한다.
     * 주기 : 10초
     */
    @Scheduled(fixedDelay = 10000)
    public void moveWaitingToEnter() {
        queueService.moveWaitingToEnter();
    }
}
