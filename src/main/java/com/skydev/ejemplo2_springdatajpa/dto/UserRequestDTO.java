package com.skydev.ejemplo2_springdatajpa.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Last name min 2 and max 50")
    private String name;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name min 2 and max 50")
    private String lastName;
    @NotNull(message = "Bith date is required")
    @Past(message = "The date of birth must be in the past")
    private LocalDate birthDate;
    @NotBlank(message = "Email is requiered")
    @Email(message = "Invalid email")
    private String email;

}
