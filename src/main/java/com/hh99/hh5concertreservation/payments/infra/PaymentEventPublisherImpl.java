package com.hh99.hh5concertreservation.payments.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.payments.domain.event.IPaymentEventPublisher;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisherImpl implements IPaymentEventPublisher {
//    private final ApplicationEventPublisher eventPublisher;
//    public void success(PaymentCompleteEvent event) {
//        eventPublisher.publishEvent(event);
//    }
    private final PaymentKafkaProducer paymentKafkaProducer;
    private final ObjectMapper mapper;
    @Override
    public void success(PaymentCompleteEvent event){
        try {
            paymentKafkaProducer.sendMessage("payment-complete", mapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}