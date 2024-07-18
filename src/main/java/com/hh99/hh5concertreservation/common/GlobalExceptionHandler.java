package com.hh99.hh5concertreservation.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleCustomException(CustomException e) {
        log.error("handleCustomException: {}", e.toString());
        return ResponseEntity.ok(e.toString());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error("Exception: {}", e.toString());
        return ResponseEntity.ok(e.toString());
    }
}
