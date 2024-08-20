package com.skydev.ejemplo2_springdatajpa.exception.custom;

import java.util.List;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{

    private List<String> errorDetails;

    public EntityNotFoundException(String message, List<String> errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

}
