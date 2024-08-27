package com.skydev.ejemplo2_springdatajpa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.skydev.ejemplo2_springdatajpa.dto.UserRequestDTO;
import com.skydev.ejemplo2_springdatajpa.dto.UserResponseDTO;
import com.skydev.ejemplo2_springdatajpa.entity.User;
import com.skydev.ejemplo2_springdatajpa.exception.custom.EntityNotFoundException;
import com.skydev.ejemplo2_springdatajpa.repository.IUserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> listaUsers;

    @BeforeEach
    void setUp() {
        listaUsers = Arrays.asList(
                new User(1L, "Anyelo", "Nuñez", LocalDate.of(2000, 6, 2), "anyelonuob2004@"),
                new User(2L, "Maria", "Fernandez", LocalDate.of(1998, 5, 12), "mariafeer@"),
                new User(3L, "Jose", "Muita", LocalDate.of(2004, 8, 20), "anyelonuob2004@"),
                new User(4L, "Sofia", "Martinez", LocalDate.of(2003, 2, 1), "sofiaMar@"));

    }

    @Nested
    class TestFindAllUsers {

        @Test
        @Tag("Succesful")
        @DisplayName("test-findAll()")
        void testFindAllUsers() {
            // Given
            when(userRepository.findAll()).thenReturn(listaUsers);
            // When
            List<UserResponseDTO> list = userService.findAllUsers();
            // Then
            assertAll(
                    () -> assertNotNull(list, () -> "NO ES NULO"),
                    () -> assertEquals(4, list.size()),
                    () -> verify(userRepository, times(1)).findAll());

        }

        @Test
        @Tag("Unsuccesful")
        void testFindAllUserListEmpty() {
            // Given
            when(userRepository.findAll()).thenReturn(Arrays.asList());
            // When
            List<UserResponseDTO> list = userService.findAllUsers();
            // Then
            assertAll(
                    () -> assertNotNull(list, () -> "NO ES NULO"),
                    () -> assertEquals(0, list.size()),
                    () -> verify(userRepository, times(1)).findAll());
        }

    }

    @Nested
    class TestFindAllUsersPage{
        
        @Test
        @Tag("Succesful")
        void testFindAllUsersPaginated() {
            // Given
            int pageNumber = 0;
            int pageSize = 2;
            String orderBy = "name";
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
            Page<User> userPage = new PageImpl<>(listaUsers.subList(0, 2), pageable, listaUsers.size());

            when(userRepository.findAll(pageable)).thenReturn(userPage);

            // When
            Page<UserResponseDTO> result = userService.findAllUsersPag(pageNumber, pageSize, orderBy);

            // Then
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(2, result.getSize()),
                    () -> verify(userRepository, times(1)).findAll(pageable));
        }
        
    }
    
    @Nested
    class TestSaveUser {

        @Test
        @Tag("Succesful")
        void testSaveUser() {

            // Given
            UserRequestDTO userToSave = new UserRequestDTO("Anyelo", "Nuñez", LocalDate.of(2004, 6, 2),
                    "anyelonuob2004@gmail.com");
            User savedUser = new User(1L, "Anyelo", "Nuñez", LocalDate.of(2004, 6, 2), "anyelonuob2004@gmail.com");
            UserResponseDTO userExpect = new UserResponseDTO(1L, "Anyelo", "Nuñez", LocalDate.of(2004, 6, 2),
                    "anyelonuob2004@gmail.com");

            // When
            when(userRepository.save(any())).thenReturn(savedUser);
            UserResponseDTO resultReal = userService.saveUser(userToSave);
            // Then
            assertAll(
                    () -> assertNotNull(resultReal),
                    () -> assertEquals(userExpect.getId(), resultReal.getId()),
                    () -> verify(userRepository, times(1)).save(any()));

        }

    }

    @Nested
    class TestUpdateUser {

        @Test
        @Tag("Succesful")
        void updateUserSuccesful() {
            // Given
            Long idUpdated = 2L;
            UserRequestDTO userUpdate = new UserRequestDTO("Joel", "Martinez", LocalDate.now(), "awa");
            User updateUser = new User(2L, "Joel", "Martinez", LocalDate.now(), "awa");
            // When
            when(userRepository.existsById(idUpdated)).thenReturn(true);
            when(userRepository.save(any(User.class))).thenReturn(updateUser);
            UserResponseDTO resultExpect = userService.updateUser(idUpdated, userUpdate);
            // Then
            assertAll(
                    () -> assertNotNull(resultExpect),
                    () -> assertEquals(updateUser.getId(), resultExpect.getId()),
                    () -> verify(userRepository).existsById(any()),
                    () -> verify(userRepository).save(any()));
        }

        @Test
        @Tag("Unsuccesful")
        void updateUserIdNotFound() {
            // Given
            Long idUpdated = 205L;
            UserRequestDTO userUpdate = new UserRequestDTO("Joel", "Martinez", LocalDate.now(), "awa");
            // When
            when(userRepository.existsById(idUpdated)).thenReturn(false);
            // Then
            assertThrows(EntityNotFoundException.class, () -> {
                userService.updateUser(idUpdated, userUpdate);
            });
            assertAll(
                    () -> verify(userRepository, times(1)).existsById(any()),
                    () -> verify(userRepository, times(0)).save(any()));
        }

    }

    @Nested
    class TestDeleteUser {

        @Test
        @Tag("Succesful")
        void testDeleteSuccesful() {
            // Given
            Long idEliminated = 1L;
            Optional<User> userFoundOptional = Optional.of(new User(1L, "Anyelo", "Nuñez", LocalDate.now(), "wasa"));
            // When
            when(userRepository.findById(anyLong())).thenReturn(userFoundOptional);
            doNothing().when(userRepository).deleteById(anyLong());
            UserResponseDTO userResult = userService.deleteUser(idEliminated);
            // Then
            assertAll(
                    () -> assertNotNull(userResult),
                    () -> assertEquals(userFoundOptional.get().getId(), userResult.getId()),
                    () -> assertEquals(userFoundOptional.get().getName(), userResult.getName()),
                    () -> assertEquals(userFoundOptional.get().getEmail(), userResult.getEmail()),
                    () -> verify(userRepository, times(1)).findById(anyLong()),
                    () -> verify(userRepository, times(1)).deleteById(anyLong()));
        }

        @Test
        @Tag("Unsuccesful")
        void testDeleteIdNotFound() {
            // Given
            Long idEliminated = 200L;
            // When
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
            // Then
            assertThrows(EntityNotFoundException.class, () -> {
                userService.deleteUser(idEliminated);
            });
            assertAll(
                    () -> verify(userRepository, times(1)).findById(anyLong()),
                    () -> verify(userRepository, times(0)).deleteById(anyLong()));
        }

    }

}
