package com.hh99.hh5concertreservation.waiting.interfaces.presentation;

import com.hh99.hh5concertreservation.waiting.domain.QueueService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class TokenValidationAspect {

    private final HttpServletRequest request;
    private final QueueService queueService;

    @Pointcut("within(com.hh99.hh5concertreservation.concert.presentation.ConcertController.*)")
    public void validateToken() {
        String token = request.getHeader("Token");
        Long userId = Long.valueOf(request.getHeader("UserId"));

        if (token == null || !queueService.isValidate(userId, token)) {
            throw new IllegalArgumentException("Invalid or missing token");
        }
    }
}
