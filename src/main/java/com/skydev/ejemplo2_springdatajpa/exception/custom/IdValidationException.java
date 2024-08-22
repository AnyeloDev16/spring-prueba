package com.skydev.ejemplo2_springdatajpa.exception.custom;

import java.util.List;

import lombok.Getter;

@Getter
public class IdValidationException extends RuntimeException{

    private List<String> errorDetails;

    public IdValidationException(String message, List<String> errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

}
