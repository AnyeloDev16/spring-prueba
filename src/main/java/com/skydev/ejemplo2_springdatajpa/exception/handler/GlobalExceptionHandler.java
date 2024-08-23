package com.skydev.ejemplo2_springdatajpa.exception.handler;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skydev.ejemplo2_springdatajpa.exception.ErrorResponse;
import com.skydev.ejemplo2_springdatajpa.exception.custom.EntityNotFoundException;
import com.skydev.ejemplo2_springdatajpa.exception.custom.IdValidationException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdValidationException.class)
    public ResponseEntity<ErrorResponse> handlerIdValidation(IdValidationException ve){
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                                                                .menssage(ve.getMessage())
                                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                                .timestamp(LocalDateTime.now())
                                                                .errorDetails(ve.getErrorDetails())
                                                                .build());
    } 

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerEntityNotFound(EntityNotFoundException enfe){
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                                                                .menssage(enfe.getMessage())
                                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                                .timestamp(LocalDateTime.now())
                                                                .errorDetails(enfe.getErrorDetails())
                                                                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValid(MethodArgumentNotValidException manv){
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                                                                .menssage("Error en la validación de datos")
                                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                                .timestamp(LocalDateTime.now())
                                                                .errorDetails(manv.getFieldErrors().stream()
                                                                                                .map(e -> e.getDefaultMessage()).toList())
                                                                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlerConstraintViolation(ConstraintViolationException cve){
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                                                                .menssage("Error entry data")
                                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                                .timestamp(LocalDateTime.now())
                                                                .errorDetails(Arrays.asList(cve.getMessage()))
                                                                .build());
    }

    // PropertyReferenceException

}
