package com.hh99.hh5concertreservation.payments.application;

import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import com.hh99.hh5concertreservation.payments.application.dto.PaymentCommand;
import com.hh99.hh5concertreservation.payments.application.dto.PaymentResult;
import com.hh99.hh5concertreservation.payments.domain.PaymentEntity;
import com.hh99.hh5concertreservation.payments.domain.PaymentService;
import com.hh99.hh5concertreservation.user.domain.PointService;
import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentUsecase {
    private final ConcertService concertService;
    private final PaymentService paymentService;
//    private final UserService userService;
    private final PointService pointService;
    private final QueueService queueService;

    @Transactional
    public PaymentResult pay(String token, PaymentCommand command) {
        ReservationEntity reservation = concertService.findTempRevervation(command.getConcertDescId(), command.getSeatNo(), 1);
        Long price = concertService.findPrice(command.getConcertDescId());
        ReservationEntity reservationEntity = concertService.confirmReservation(reservation);
        pointService.subtractPoint(command.getUserId(), price);
        PaymentEntity payment = paymentService.savePayment(reservationEntity, price);
        queueService.expireTokenFromActive(token);
        return new PaymentResult(payment);
    }
}
