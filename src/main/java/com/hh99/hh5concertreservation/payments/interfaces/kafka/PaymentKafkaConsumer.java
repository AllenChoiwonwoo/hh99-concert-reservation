package com.hh99.hh5concertreservation.payments.interfaces.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.application.PaymentUsecase;
import com.hh99.hh5concertreservation.payments.application.dto.SendPaymentDataCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {
    private final PaymentUsecase paymentUsecase;
    private final ObjectMapper mapper;


    @KafkaListener(topics = "payment-complete", groupId = "payment-consumers")
    public void listen(String message) {
        paymentUsecase.sendPaymentData(new SendPaymentDataCommand(message, mapper));
    }
}
