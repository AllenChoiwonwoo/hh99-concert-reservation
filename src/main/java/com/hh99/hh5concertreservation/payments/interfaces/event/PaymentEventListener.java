package com.hh99.hh5concertreservation.payments.interfaces.event;

import com.hh99.hh5concertreservation.payments.application.PaymentUsecase;
import com.hh99.hh5concertreservation.payments.application.dto.SendPaymentDataCommand;
import com.hh99.hh5concertreservation.payments.domain.event.PaymentCompleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private final PaymentUsecase paymentUsecase;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendPaymentData(PaymentCompleteEvent event) {
        paymentUsecase.sendPaymentData(new SendPaymentDataCommand(event));
    }
}