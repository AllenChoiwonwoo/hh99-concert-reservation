package com.hh99.hh5concertreservation.waiting.interfaces.presentation;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CreateTokenResult;
import com.hh99.hh5concertreservation.waiting.interfaces.presentation.dto.TokenResponse;
import com.hh99.hh5concertreservation.waiting.interfaces.presentation.dto.WaitingOrderResponse;
import com.hh99.hh5concertreservation.waiting.interfaces.presentation.dto.TokenRequest;
import com.hh99.hh5concertreservation.waiting.application.usecase.QueueUsecase;
import com.hh99.hh5concertreservation.waiting.interfaces.presentation.dto.WaitingOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final QueueUsecase queueUsecase;

    /**
     * @param tokenRequest
     * @return
     */
    @PostMapping
    public ResponseEntity createToken(@RequestBody TokenRequest tokenRequest) {
        try {
            CreateTokenCommand createTokenCommand = tokenRequest.toCommand();

            CreateTokenResult result = queueUsecase.addWaitlist(createTokenCommand);
            log.info(result.toString());
            return ResponseEntity.ok(new TokenResponse(result));
        }catch (Exception e) {
            log.error(e.toString());
            return (ResponseEntity) ResponseEntity.internalServerError();
        }
    }

    @PostMapping("/check") // TOFIX : 레스트하지 못하는것 같다.
    public ResponseEntity checkState(@RequestBody WaitingOrderRequest request) {
        CheckStateCommand command = request.toCommand();
        CheckStateResult checkStateResult = queueUsecase.checkWaitingStatus(command);
        return ResponseEntity.ok(new WaitingOrderResponse(checkStateResult));
    }

}
