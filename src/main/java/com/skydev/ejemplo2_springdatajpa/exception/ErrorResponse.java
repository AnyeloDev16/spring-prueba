package com.skydev.ejemplo2_springdatajpa.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String menssage;
    private Integer statusCode;
    private LocalDateTime timestamp;
    private List<String> errorDetails;

}
