package com.hh99.hh5concertreservation.payments.application;

import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.payments.application.dto.PaymentCommand;
import com.hh99.hh5concertreservation.payments.application.dto.PaymentResult;
import com.hh99.hh5concertreservation.payments.application.dto.SendPaymentDataCommand;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteEvent;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentEventPublisher;
import com.hh99.hh5concertreservation.waiting.domain.event.TokenExpireEvent;
import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import com.hh99.hh5concertreservation.payments.domain.PaymentService;
import com.hh99.hh5concertreservation.user.domain.PointService;
import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import com.hh99.hh5concertreservation.waiting.domain.event.TokenEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentUsecase {
    private final ConcertService concertService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final TokenEventPublisher tokenEventPublisher;
    private final PaymentEventPublisher paymentEventPublisher;

    @Transactional
    public PaymentResult pay(String token, PaymentCommand command) {
        ReservationEntity reservation = concertService.findTempRevervationOrThrow(command.getConcertDescId(), command.getSeatNo(), 1);
        Long price = concertService.findPrice(command.getConcertDescId());
        ReservationEntity reservationEntity = concertService.confirmReservation(reservation);
        pointService.subtractPoint(command.getUserId(), price);
        PaymentEntity payment = paymentService.savePayment(reservationEntity, price);

        // 결제 성공시 토근 만료 이벤트 발행
        tokenEventPublisher.success(new TokenExpireEvent(token));
        // 결제 정보를 데이터 플랫폼으로 전송
        paymentEventPublisher.success(new PaymentCompleteEvent(payment));
        return new PaymentResult(payment);
    }

    public void sendPaymentData(SendPaymentDataCommand command) {
        paymentService.sendPaymentData(command.toPayment());

    }
}
