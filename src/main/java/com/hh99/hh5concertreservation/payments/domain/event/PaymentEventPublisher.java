package com.hh99.hh5concertreservation.payments.domain.event;

import org.springframework.kafka.support.SendResult;
import java.util.concurrent.CompletableFuture;

public interface PaymentEventPublisher {
    CompletableFuture<SendResult<String, String>> success(PaymentCompleteEvent event);
}
