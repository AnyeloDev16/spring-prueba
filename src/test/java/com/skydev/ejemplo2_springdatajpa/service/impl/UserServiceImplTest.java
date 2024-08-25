package com.skydev.ejemplo2_springdatajpa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;
import com.skydev.ejemplo2_springdatajpa.entity.User;
import com.skydev.ejemplo2_springdatajpa.repository.IUserRepository;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> listaUsers;

    @BeforeEach
    void setUp(){
        listaUsers = Arrays.asList(
            new User(1L, "Anyelo", "Nuñez", LocalDate.of(2000, 6, 2), "anyelonuob2004@"),
            new User(2L, "Maria", "Fernandez", LocalDate.of(1998, 5, 12), "mariafeer@"),
            new User(3L, "Jose", "Muita", LocalDate.of(2004, 8, 20), "anyelonuob2004@"),
            new User(4L, "Sofia", "Martinez", LocalDate.of(2003, 2, 1), "sofiaMar@")
        );

    }

    @Nested
    class TestFindAllUsers{
        
        @Test
        @DisplayName("test-findAll()")
        void testFindAllUsers(){
            //Given
            when(userRepository.findAll()).thenReturn(listaUsers);
            //When
            List<UserResponseDTO> list = userService.findAllUsers();
            //Then
            assertAll(
                () -> assertNotNull(list, ()->"NO ES NULO"),
                () -> assertEquals(4, list.size()),
                () -> verify(userRepository, times(1)).findAll()
            );
        
        }

        @Test
        void testFindAllUserListEmpty(){
            //Given
            when(userRepository.findAll()).thenReturn(Arrays.asList());
            //When
            List<UserResponseDTO> list = userService.findAllUsers();
            //Then
            assertAll(
                () -> assertNotNull(list, ()->"NO ES NULO"),
                () -> assertEquals(0, list.size()),
                () -> verify(userRepository, times(1)).findAll()
            );
        }

    }

    @Nested
    class TestSaveUser{

        @Test
        void testSaveUser(){

            //Given
            UserRequestDTO userToSave = new UserRequestDTO("Anyelo", "Nuñez", LocalDate.of(2004,6,2), "anyelonuob2004@gmail.com");
            User savedUser = new User(1L, "Anyelo", "Nuñez", LocalDate.of(2004,6,2), "anyelonuob2004@gmail.com");
            UserResponseDTO userExpect = new UserResponseDTO(1L, "Anyelo", "Nuñez", LocalDate.of(2004,6,2), "anyelonuob2004@gmail.com");

            when(userRepository.save(any())).thenReturn(savedUser);
            
            //When
            UserResponseDTO resultReal = userService.saveUser(userToSave);
            //Then
            assertAll(
                () -> assertNotNull(resultReal),
                () -> assertEquals(userExpect.getId(), resultReal.getId()),
                () -> verify(userRepository, times(1)).save(any())
            );
        
        }

    }

}
