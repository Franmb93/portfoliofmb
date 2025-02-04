package com.franmunozbetanzos.portfolio.service;

import com.franmunozbetanzos.portfolio.dto.UserInfoDTO;
import com.franmunozbetanzos.portfolio.mapper.UserToUserInfoDTOMapper;
import com.franmunozbetanzos.portfolio.model.Role;
import com.franmunozbetanzos.portfolio.model.User;
import com.franmunozbetanzos.portfolio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserToUserInfoDTOMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserInfoDTO userInfoDTO;
    private Set<Role> roles;

    @BeforeEach
    void setUp() {
        // Crear roles de prueba
        roles = new HashSet<>();
        Role role = Role.builder()
                .id(1L)
                .name("USER")
                .build();
        roles.add(role);

        // Crear usuario de prueba
        user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password123")
                .roles(roles)
                .build();

        // Crear DTO de prueba
        userInfoDTO = new UserInfoDTO(1L, "testUser", roles);
    }

    @Test
    void getAllUsersInfo_ShouldReturnListOfUserInfoDTO() {
        // Arrange
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toUserInfoDTO(user)).thenReturn(userInfoDTO);

        // Act
        List<UserInfoDTO> result = userService.getAllUsersInfo();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userInfoDTO, result.getFirst());
        assertEquals("testUser", result.getFirst()
                .username());
        assertEquals(roles, result.getFirst()
                .roles());
        verify(userRepository).findAll();
        verify(userMapper).toUserInfoDTO(user);
    }

    @Test
    void getAllUsersInfo_WhenNoUsers_ShouldReturnEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act
        List<UserInfoDTO> result = userService.getAllUsersInfo();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
        verify(userMapper, never()).toUserInfoDTO(any());
    }

    @Test
    void getUserInfoById_WhenUserExists_ShouldReturnUserInfoDTO() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserInfoDTO(user)).thenReturn(userInfoDTO);

        // Act
        UserInfoDTO result = userService.getUserInfoById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("testUser", result.username());
        assertEquals(roles, result.roles());
        verify(userRepository).findById(1L);
        verify(userMapper).toUserInfoDTO(user);
    }

    @Test
    void getUserInfoById_WhenUserDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        UserInfoDTO result = userService.getUserInfoById(1L);

        // Assert
        assertNull(result);
        verify(userRepository).findById(1L);
        verify(userMapper, never()).toUserInfoDTO(any());
    }
}