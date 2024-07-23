package com.hh99.hh5concertreservation.payments.presentation;

import com.hh99.hh5concertreservation.payments.application.PaymentUsecase;
import com.hh99.hh5concertreservation.payments.application.dto.PaymentResult;
import com.hh99.hh5concertreservation.payments.presentation.dto.PaymentRequest;
import com.hh99.hh5concertreservation.payments.presentation.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentUsecase paymentUsecase;

    @PostMapping
    public ResponseEntity pay(@RequestBody PaymentRequest request){
        PaymentResult result = paymentUsecase.pay(request.toCommand());
        return ResponseEntity.ok(new PaymentResponse(result));
    }
}
