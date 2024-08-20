package com.skydev.ejemplo2_springdatajpa.service;

import java.util.List;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;

public interface IUserService {
    
    List<UserResponseDTO> findAllUsers();
    UserResponseDTO saveUser(UserRequestDTO UserRequestDTO);
    UserResponseDTO updateUser(Long id, UserRequestDTO UserRequestDTO);
    void deleteUser(Long id);

}
