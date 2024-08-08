package com.hh99.hh5concertreservation.waiting.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TokenEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void success(TokenExpireEvent event) {
        eventPublisher.publishEvent(event);
    }
}