package com.skydev.ejemplo2_springdatajpa.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;
import com.skydev.ejemplo2_springdatajpa.entity.User;
import com.skydev.ejemplo2_springdatajpa.exception.custom.EntityNotFoundException;
import com.skydev.ejemplo2_springdatajpa.repository.IUserRepository;
import com.skydev.ejemplo2_springdatajpa.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

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
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        boolean isExists = userRepository.existsById(id);
        if(!isExists){
            throw new EntityNotFoundException("Search error", List.of("User id not found : "+ id));
        }
        User user = modelMapper.map(userRequestDTO, User.class);
        user.setId(id);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);

    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        boolean isExists = userRepository.existsById(id);
        if(!isExists){
            throw new EntityNotFoundException("Search error", List.of("User id not found : "+ id));
        }
        userRepository.deleteById(id);
    }

}
