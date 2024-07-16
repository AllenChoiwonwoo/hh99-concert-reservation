package com.hh99.hh5concertreservation.payments.domain;

import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public PaymentEntity savePayment(ReservationEntity reservationEntity, Long price) {
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .userId(reservationEntity.getUserId()).status(2).reservationId(reservationEntity.getId()).amount(price).build();
        PaymentEntity payment = paymentRepository.save(paymentEntity);
        return payment;
    }
}
