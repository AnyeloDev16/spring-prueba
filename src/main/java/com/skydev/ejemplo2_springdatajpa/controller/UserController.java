package com.skydev.ejemplo2_springdatajpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;
import com.skydev.ejemplo2_springdatajpa.exception.custom.IdValidationException;
import com.skydev.ejemplo2_springdatajpa.service.IEmailService;
import com.skydev.ejemplo2_springdatajpa.service.IUserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final IUserService userService;
    private final IEmailService emailService;

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserResponseDTO userResDTO = userService.saveUser(userRequestDTO);
        emailService.sendEmailRegisterSuccessful(userResDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable @Positive(message = "Invalid ID") Long id ,@RequestBody @Valid UserRequestDTO userRequestDTO) {
        List<UserResponseDTO> list = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(list.get(0));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive(message = "Invalid ID") Long id){
        UserResponseDTO userResDTO = userService.deleteUser(id);
        emailService.sendEmailDeleteSuccessful(userResDTO.getEmail());
        return ResponseEntity.noContent().build();
    }

}
