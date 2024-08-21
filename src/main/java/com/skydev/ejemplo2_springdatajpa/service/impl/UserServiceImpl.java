package com.skydev.ejemplo2_springdatajpa.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;
import com.skydev.ejemplo2_springdatajpa.entity.User;
import com.skydev.ejemplo2_springdatajpa.exception.custom.EntityNotFoundException;
import com.skydev.ejemplo2_springdatajpa.repository.IUserRepository;
import com.skydev.ejemplo2_springdatajpa.service.IUserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll()
                                .stream()
                                .map(userEntity -> modelMapper.map(userEntity, UserResponseDTO.class))
                                .toList();
    }

    @Override
    @Transactional
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    @Transactional
    public List<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("Search error", List.of("User id not found : "+ id));
        }
        // Previous
        User previousUser = optionalUser.get();
        UserResponseDTO previousUserDTO = modelMapper.map(previousUser, UserResponseDTO.class);
        // New
        User user = modelMapper.map(userRequestDTO, User.class);
        user.setId(id);
        userRepository.save(user);
        UserResponseDTO newUserDTO = modelMapper.map(user, UserResponseDTO.class);
        
        return new ArrayList<UserResponseDTO>(Arrays.asList(newUserDTO, previousUserDTO));

    }

    @Override
    @Transactional
    public UserResponseDTO deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("Search error", List.of("User id not found : "+ id));
        }
        //previous
        User previousUser = optionalUser.get();
        UserResponseDTO previousUserDTO = modelMapper.map(previousUser, UserResponseDTO.class);
        // Delete
        userRepository.deleteById(id);
        return previousUserDTO;

    }

}
