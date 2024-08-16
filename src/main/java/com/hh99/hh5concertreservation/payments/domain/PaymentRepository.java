package com.hh99.hh5concertreservation.payments.domain;

import com.hh99.hh5concertreservation.payments.infra.PaymentCompleteEventEntity;

import java.util.List;

//@Component
public interface PaymentRepository {
    PaymentEntity save(PaymentEntity paymentEntity);

    void sendPaymentData(PaymentEntity event);

    PaymentCompleteEventEntity saveInitEvent(Long paymentId, String message);


    PaymentCompleteEventEntity updateEventState(PaymentCompleteEventEntity published);

    PaymentCompleteEventEntity findEventByPaymentId(Long paymentId);

    List<PaymentCompleteEventEntity> findAllInitEvents();

    List<PaymentCompleteEventEntity> findAllEvent();

    void saveAll(List<PaymentCompleteEventEntity> expiredEvents);
}
