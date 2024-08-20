package com.skydev.ejemplo2_springdatajpa.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skydev.ejemplo2_springdatajpa.exception.ErrorResponse;
import com.skydev.ejemplo2_springdatajpa.exception.custom.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handlerValidationException(ValidationException ve){
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                                                                .menssage(ve.getMessage())
                                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                                .timestamp(LocalDateTime.now())
                                                                .errorDetails(ve.getErrorDetails())
                                                                .build());
    }   

}
