package com.hh99.hh5concertreservation.payments.interfaces.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteOutboxManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The type Payment complete outbox scheduler.
 */
@RequiredArgsConstructor
@Component
@EnableScheduling
public class PaymentCompleteOutboxScheduler {
    private final PaymentCompleteOutboxManager outboxManager;
    private final ObjectMapper mapper;

    /**
     * 전송되지 않은 이밴트 재시도
     *  30초에 한번씩
     */
    @Scheduled(fixedRate = 30000)
    public void checkOutbox() {
        outboxManager.retry();
    }

    /**
     * 오레된 아웃박스 이벤트 제거
     *  매일 03시를 기준으로 하루지난 outbox 이벤트 제거
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void removeExpired() {
        outboxManager.removeExpired();
    }
}
