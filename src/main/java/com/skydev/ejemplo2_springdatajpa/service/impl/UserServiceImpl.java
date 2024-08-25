package com.skydev.ejemplo2_springdatajpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll()
                                .stream()
                                .map(userEntity -> modelMapper.map(userEntity, UserResponseDTO.class))
                                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllUsersPag(int pageNumber, int pageSize, String orderBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        Page<User> pageUser = userRepository.findAll(pageable);
        return pageUser.map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Override
    @Transactional
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        boolean isExists = userRepository.existsById(id);
        if(!isExists){
            throw new EntityNotFoundException("Search error", List.of("User id not found : "+ id));
        }
        // New
        User user = modelMapper.map(userRequestDTO, User.class);
        user.setId(id);
        userRepository.save(user);
        return  modelMapper.map(user, UserResponseDTO.class);
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
