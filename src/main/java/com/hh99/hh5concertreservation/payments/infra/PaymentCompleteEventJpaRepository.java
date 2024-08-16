package com.hh99.hh5concertreservation.payments.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentCompleteEventJpaRepository extends JpaRepository<PaymentCompleteEventEntity, Long> {
    PaymentCompleteEventEntity findByPaymentId(Long paymentId);

    Optional<List<PaymentCompleteEventEntity>> findAllByStatus(int State);
}
