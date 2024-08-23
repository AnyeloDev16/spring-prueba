package com.skydev.ejemplo2_springdatajpa.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;
import com.skydev.ejemplo2_springdatajpa.service.IEmailService;
import com.skydev.ejemplo2_springdatajpa.service.IUserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
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

    @GetMapping("/findAllPage")
    public ResponseEntity<Page<UserResponseDTO>> findAllUsersPag(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Invalid page number (min 0)") int pageNumber, @RequestParam(defaultValue = "10") @Min(value = 1, message = "Invalid page size (min 1)") int pageSize, @RequestParam(defaultValue = "id") String orderBy) {
        return ResponseEntity.ok(userService.findAllUsersPag(pageNumber, pageSize, orderBy));
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserResponseDTO userResDTO = userService.saveUser(userRequestDTO);
        emailService.sendEmailRegisterSuccessful(userResDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable @Positive(message = "Invalid ID") Long id ,@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive(message = "Invalid ID") Long id){
        UserResponseDTO userResDTO = userService.deleteUser(id);
        emailService.sendEmailDeleteSuccessful(userResDTO.getEmail());
        return ResponseEntity.noContent().build();
    }

}
