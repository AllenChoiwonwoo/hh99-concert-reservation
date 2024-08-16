package com.hh99.hh5concertreservation.payments.domain.event;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IPaymentEventPublisher {
    void success(PaymentCompleteEvent event) throws JsonProcessingException;
}
