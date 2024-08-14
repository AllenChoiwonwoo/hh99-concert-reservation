package com.hh99.hh5concertreservation.waiting.interfaces.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteEvent;
import com.hh99.hh5concertreservation.waiting.application.usecase.QueueUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenKafkaConsumer {
    private final QueueUsecase queueUsecase;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "payment-complete", groupId = "token-consumers")
    public void listen(String message) {
//        log.info(message);
        try {
            PaymentCompleteEvent event = mapper.readValue(message, PaymentCompleteEvent.class);
            queueUsecase.expireToken(event.getToken());
        }catch (JsonProcessingException e){
            log.error(e.getMessage());
        }
    }
}
