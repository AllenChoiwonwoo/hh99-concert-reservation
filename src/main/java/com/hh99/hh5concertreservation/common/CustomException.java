package com.hh99.hh5concertreservation.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Getter
@Slf4j
public class CustomException extends RuntimeException{
    private String code;
    private String message;

    public CustomException(ErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    @Getter
    @RequiredArgsConstructor
    public enum ErrorEnum {
        // Success
        SUCCESS("0000", "SUCCESS"),

        // Token errors
        TOKEN_EXPIRED("1000", "TOKEN IS EXPIRED"),
        NO_MORE_ACTIVE_TOKEN("1001", "AVAILABLE ACTIVE TOKEN IS FULL"),
        TOKEN_INACTIVE("1002", "TOKEN IS INACTIVE"),
        NO_TOKEN("1003", "NO TOKEN"),

        // Concert errors
        NO_CONCERT("2000", "CONCERT DOES NOT EXIST"),
        RESERVED_SEAT("2001", "SEAT IS ALREADY RESERVED"),
        RESERVED_SEAT2("2003", "SEAT IS ALREADY RESERVED - when select DB"),
        RESERVED_SEAT3("2004", "SEAT IS ALREADY RESERVED - when upodate DB"),
        RESERVED_SEAT4("2005", "SEAT IS ALREADY RESERVED - when upodate DB - OPTIMISTIC_LOCK"),


        NO_SEAT("2002", "NO SEAT AVAILABLE"),

        // Point errors
        MEMBER_NOT_FOUND("3000", "MEMBER DOES NOT EXIST"),
        MEMBER_NOT_MATCH("3001", "MEMBER INFO DOES NOT MATCH"),

        // Payment errors
        PAYMENT_FAILED("4000", "PAYMENT FAILED"),

        // General errors
        UNKNOWN_ERROR("9999", "UNKNOWN ERROR");

        private final String code;
        private final String message;
    }
}
