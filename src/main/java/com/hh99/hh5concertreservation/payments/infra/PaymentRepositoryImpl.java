package com.hh99.hh5concertreservation.payments.infra;

import com.hh99.hh5concertreservation.payments.domain.PaymentRepository;
import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository repo;

    /**
     * @param paymentEntity
     * @return
     */
    @Override
    public PaymentEntity save(PaymentEntity paymentEntity) {
        return repo.save(paymentEntity);
    }

    /**
     * 외부 데이터 서버로 결제 데이터 전송
     * @param event
     */
    @Override
    public void sendPaymentData(PaymentEntity event) {
        //todo : 외부 데이터 서버로 전송 로직 추가
        return;
    }
}
