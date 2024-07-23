package com.hh99.hh5concertreservation.payments.domain;

import org.springframework.stereotype.Component;

@Component
public interface IPaymentRepository {
    PaymentEntity save(PaymentEntity paymentEntity);
}
