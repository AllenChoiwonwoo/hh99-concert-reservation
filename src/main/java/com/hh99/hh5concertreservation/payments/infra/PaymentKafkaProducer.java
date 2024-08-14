package com.hh99.hh5concertreservation.payments.infra;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class PaymentKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PaymentKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, String>> sendMessage(String topic, String message) {
        return kafkaTemplate.send(topic, message);
    }
}
