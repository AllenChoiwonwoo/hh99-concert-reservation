package com.hh99.hh5concertreservation.payments.infra;

import com.hh99.hh5concertreservation.payments.domain.IPaymentRepository;
import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements IPaymentRepository {

    private final PaymentJpaRepository repo;

    /**
     * @param paymentEntity
     * @return
     */
    @Override
    public PaymentEntity save(PaymentEntity paymentEntity) {
        return repo.save(paymentEntity);
    }
}
