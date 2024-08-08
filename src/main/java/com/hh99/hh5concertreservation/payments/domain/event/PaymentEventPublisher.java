package com.hh99.hh5concertreservation.payments.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    public void success(PaymentCompleteEvent event) {
        eventPublisher.publishEvent(event);
    }
}