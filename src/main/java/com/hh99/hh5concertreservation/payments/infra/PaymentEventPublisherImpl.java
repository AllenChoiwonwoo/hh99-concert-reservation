package com.hh99.hh5concertreservation.payments.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentEventPublisher;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisherImpl implements PaymentEventPublisher {

    private final PaymentKafkaProducer paymentKafkaProducer;
    private final ObjectMapper mapper;

//    private final ApplicationEventPublisher eventPublisher;
//    public void success(PaymentCompleteEvent event) {
//        eventPublisher.publishEvent(event);
//    }

//    private final PaymentCompleteOutboxManager outboxManager;
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public CompletableFuture<SendResult<String, String>> success(PaymentCompleteEvent event) {
        try {
            return paymentKafkaProducer.sendMessage("payment-complete",event.getPaymentId(), mapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}