package com.hh99.hh5concertreservation.payments.interfaces.kafka;

import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteOutboxManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentKafkaOutBoxConsumer {
    private final PaymentCompleteOutboxManager outboxManager;

    @Transactional
    @KafkaListener(topics = "payment-complete", groupId = "payment-outbox-consumers")
    public void listen(String message) {
        outboxManager.setPublished(message);
    }
}
