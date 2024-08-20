package com.skydev.ejemplo2_springdatajpa.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String name;
    private String lastname;
    private LocalDateTime birthDate;
    private String email;

}
