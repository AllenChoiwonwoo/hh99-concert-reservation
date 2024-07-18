package com.hh99.hh5concertreservation.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
public class CustomException extends RuntimeException{
    private String code;
    private String message;

    public CustomException(ErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    // 내부 enum 정의
    @Getter
    @RequiredArgsConstructor
    public enum ErrorEnum {
        SUCCESS("0000","SUCCESS"),
        TOKEN_EXPIRED("1000","TOKEN IS EXPIRED"),
        NO_MORE_ACTIVE_TOKEN("1001","AVAILABLE ACTIVE TOKEN IS FULL"),
        TOKEN_INACTIVE("1002","TOKEN IS INACTIVE"),
        NO_CONCERT("2000","CONCERT DOES NOT EXIST"),
        MEMBER_NOT_FOUND("3000", "MEMBER DOES NOT EXIST"),
        MEMBER_NOT_MATCH("4000", "MEMBER INFO IS NOT EQUAL"),
        RESERVED_SEAT("5000", "SEAT IS ALREADY RESERVED"),
        UNKNOWN_ERROR("9999","UNKNOWN_ERROR"),
        NO_SEAT("1234","NO SEAT" );

        private final String code;
        private final String message;
    }
}
