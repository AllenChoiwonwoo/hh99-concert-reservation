package com.hh99.hh5concertreservation.payments.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh99.hh5concertreservation.payments.domain.PaymentRepository;
import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository repo;
    private final PaymentCompleteEventJpaRepository outboxRepo;
    private final ObjectMapper mapper;

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

    /**
     * @param paymentId
     * @param message
     * @return
     */
    @Override
    public PaymentCompleteEventEntity saveInitEvent(Long paymentId, String message) {
        return outboxRepo.save(PaymentCompleteEventEntity.createInit(paymentId, message));
    }

    /**
     * @param published
     * @return
     */
    @Override
    public PaymentCompleteEventEntity updateEventState(PaymentCompleteEventEntity published) {
        return outboxRepo.save(published);
    }

    /**
     * @param paymentId
     * @return
     */
    @Override
    public PaymentCompleteEventEntity findEventByPaymentId(Long paymentId) {
       return outboxRepo.findByPaymentId(paymentId);
    }

    /**
     * @return
     */
    @Override
    public List<PaymentCompleteEventEntity> findAllInitEvents() {
        Optional<List<PaymentCompleteEventEntity>> result = outboxRepo.findAllByStatus(0);
        return result.orElse(List.of());
    }

    /**
     * @return
     */
    @Override
    public List<PaymentCompleteEventEntity> findAllEvent() {
        return outboxRepo.findAll();
    }

    /**
     * @param expiredEvents
     */
    @Override
    public void saveAll(List<PaymentCompleteEventEntity> expiredEvents) {
        outboxRepo.saveAll(expiredEvents);
    }
}
