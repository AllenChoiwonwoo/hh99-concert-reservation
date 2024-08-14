package com.hh99.hh5concertreservation.payments.domain.event;

public interface PaymentCompleteEventRepository {
    void save(PaymentCompleteEvent event);
}
