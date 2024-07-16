package com.hh99.hh5concertreservation.payments.domain;

import org.springframework.stereotype.Component;

@Component
public interface PaymentRepository {
    PaymentEntity save(PaymentEntity paymentEntity);
}
