package com.hh99.hh5concertreservation.payment.application;

import com.hh99.hh5concertreservation.concert.domain.ConcertService;
import com.hh99.hh5concertreservation.concert.domain.dto.PaymentCommand;
import com.hh99.hh5concertreservation.concert.domain.dto.PaymentResult;
import com.hh99.hh5concertreservation.concert.domain.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentUsecase {
    private final ConcertService concertService;
    public PaymentResult payment(PaymentCommand command) {
        Optional<ReservationEntity> reservation = concertService.findReservation(command.getConcertDescId(),
                command.getSeatNo());
        if (reservation.isPresent()) {
            throw new RuntimeException("이미 예약된 좌석입니다.");
        }
        
        
        
        
        return null;
    }
}
