package com.hh99.hh5concertreservation.payments.domain;

import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final IPaymentRepository IPaymentRepository;
    public PaymentEntity savePayment(ReservationEntity reservationEntity, Long price) {
        PaymentEntity paymentEntity = PaymentEntity.createActivePaymanet(reservationEntity.getUserId(), reservationEntity.getId(), price);
//        PaymentEntity paymentEntity = PaymentEntity.builder()
//                .userId(reservationEntity.getUserId()).status(2).reservationId(reservationEntity.getId()).amount(price).build();
        PaymentEntity payment = IPaymentRepository.save(paymentEntity);
        return payment;
    }
}
