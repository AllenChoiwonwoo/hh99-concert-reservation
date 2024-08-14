package com.hh99.hh5concertreservation.payments.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCompleteEventJpaRepository extends JpaRepository<PaymentCompleteEventEntity, Long> {
}
