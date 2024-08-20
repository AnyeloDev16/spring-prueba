package com.skydev.ejemplo2_springdatajpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;
import com.skydev.ejemplo2_springdatajpa.exception.custom.ValidationException;
import com.skydev.ejemplo2_springdatajpa.service.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequestDTO) {
        verifyData(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id ,@RequestBody UserRequestDTO userRequestDTO) {
        verifyData(userRequestDTO);
        return ResponseEntity.ok(userService.saveUser(userRequestDTO));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return null;
    }

    private void verifyData(UserRequestDTO userRequestDTO) {
      
        List<String> errorDetails = new ArrayList();

        if(userRequestDTO.getName() == null || userRequestDTO.getName().isBlank()){
            errorDetails.add("Name is empty");
        }
        if(userRequestDTO.getName() == null || userRequestDTO.getLastname().isBlank()){
            errorDetails.add("Lastname is empty");
        }
        if(userRequestDTO.getBirthDate() == null){
            errorDetails.add("Birthdate is empty");
        }
        if(userRequestDTO.getEmail() == null || userRequestDTO.getEmail().isBlank()){
            errorDetails.add("Email is empty");
        } else if (!userRequestDTO.getEmail().contains("@")){
            errorDetails.add("Email is missing the /'@/'");
        }

        if(!errorDetails.isEmpty()){
            throw new ValidationException("Data entry error", errorDetails);
        }

    }

    private void verifyId(Long id){
        if(id <= 0){
            throw new ValidationException("Data entry error", List.of("Invalid ID"));
        }
    }

}
