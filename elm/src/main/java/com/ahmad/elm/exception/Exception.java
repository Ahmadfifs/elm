package com.ahmad.elm.exception;

import org.springframework.http.HttpStatus;

public class Exception {

    private final String errorCode;
    private final HttpStatus httpStatus;

    public Exception(String errorCode, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
