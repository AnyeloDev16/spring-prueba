package com.skydev.ejemplo2_springdatajpa.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;

public interface IUserService {
    
    List<UserResponseDTO> findAllUsers();
    Page<UserResponseDTO> findAllUsersPag(int pageNumber, int pageSize, String orderBy);
    UserResponseDTO saveUser(UserRequestDTO UserRequestDTO);
    UserResponseDTO updateUser(Long id, UserRequestDTO UserRequestDTO);
    UserResponseDTO deleteUser(Long id);

}
