package com.hh99.hh5concertreservation.payments.infra;

import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long>{
}
