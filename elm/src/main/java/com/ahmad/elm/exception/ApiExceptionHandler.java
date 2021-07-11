package com.ahmad.elm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> handleException(ApiException api) {
        Exception exception = new Exception(api.getMessage() , api.getStatus());

        return new ResponseEntity<>(exception, api.getStatus());
    }
}
