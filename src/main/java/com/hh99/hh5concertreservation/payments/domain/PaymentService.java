package com.hh99.hh5concertreservation.payments.domain;

import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public PaymentEntity savePayment(ReservationEntity reservationEntity, Long price) {
        PaymentEntity paymentEntity = PaymentEntity.createActivePaymanet(reservationEntity.getUserId(), reservationEntity.getId(), price);
        PaymentEntity payment = paymentRepository.save(paymentEntity);
        return payment;
    }

    public void sendPaymentData(PaymentEntity event) {
        paymentRepository.sendPaymentData(event);
    }
}
