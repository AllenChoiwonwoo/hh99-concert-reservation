package com.hh99.hh5concertreservation.payment.presentation;

import com.hh99.hh5concertreservation.concert.domain.dto.PaymentResult;
import com.hh99.hh5concertreservation.payment.presentation.dto.PaymentRequest;
import com.hh99.hh5concertreservation.payment.presentation.dto.PaymentResponse;
import com.hh99.hh5concertreservation.payment.application.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    
    @PostMapping
    public ResponseEntity payment(@RequestBody PaymentRequest request){
        PaymentResult result = paymentService.payment(request.toCommand());
        return ResponseEntity.ok(new PaymentResponse(result));
    }
}
