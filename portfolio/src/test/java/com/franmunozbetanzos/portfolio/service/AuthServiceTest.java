package com.franmunozbetanzos.portfolio.service;

import com.franmunozbetanzos.portfolio.dto.LoginRequestDTO;
import com.franmunozbetanzos.portfolio.dto.LoginResponseDTO;
import com.franmunozbetanzos.portfolio.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "testPass";
    private static final String TOKEN = "test.jwt.token";

    private LoginRequestDTO loginRequest;
    private UserDetails userDetails;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        loginRequest = LoginRequestDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        userDetails = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(authorities)
                .build();

        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Test
    void login_ShouldReturnLoginResponse_WhenCredentialsAreValid() {
        // Given
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtService.generateToken(userDetails)).thenReturn(TOKEN);

        // When
        LoginResponseDTO response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertEquals(USERNAME, response.getUsername());
        assertEquals(TOKEN, response.getToken());
        assertArrayEquals(new String[]{"ADMIN", "USER"}, response.getRoles());
        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(jwtService).generateToken(userDetails);
    }

    @Test
    void login_ShouldThrowException_WhenCredentialsAreInvalid() {
        // Given
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Then
        assertThrows(BadCredentialsException.class, () -> {
            // When
            authService.login(loginRequest);
        });

        verify(authenticationManager).authenticate(any(Authentication.class));
        verifyNoInteractions(jwtService);
    }

    @Test
    void login_ShouldProcessAuthorities_WhenUserHasCustomRoles() {
        // Given
        List<SimpleGrantedAuthority> customAuthorities = List.of(
                new SimpleGrantedAuthority("ROLE_CUSTOM"),
                new SimpleGrantedAuthority("ROLE_SPECIAL")
        );

        UserDetails customUser = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(customAuthorities)
                .build();

        Authentication customAuth = new UsernamePasswordAuthenticationToken(
                customUser, null, customAuthorities);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(customAuth);
        when(jwtService.generateToken(customUser)).thenReturn(TOKEN);

        // When
        LoginResponseDTO response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertArrayEquals(new String[]{"CUSTOM", "SPECIAL"}, response.getRoles());
    }

    @Test
    void login_ShouldHandleEmptyRoles_WhenUserHasNoAuthorities() {
        // Given
        UserDetails userWithNoRoles = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(List.of())
                .build();

        Authentication authWithNoRoles = new UsernamePasswordAuthenticationToken(
                userWithNoRoles, null, List.of());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authWithNoRoles);
        when(jwtService.generateToken(userWithNoRoles)).thenReturn(TOKEN);

        // When
        LoginResponseDTO response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertEquals(0, response.getRoles().length);
    }

    @Test
    void login_ShouldPreserveOrder_WhenProcessingRoles() {
        // Given
        List<SimpleGrantedAuthority> orderedAuthorities = List.of(
                new SimpleGrantedAuthority("ROLE_FIRST"),
                new SimpleGrantedAuthority("ROLE_SECOND"),
                new SimpleGrantedAuthority("ROLE_THIRD")
        );

        UserDetails userWithOrderedRoles = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(orderedAuthorities)
                .build();

        Authentication authWithOrderedRoles = new UsernamePasswordAuthenticationToken(
                userWithOrderedRoles, null, orderedAuthorities);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authWithOrderedRoles);
        when(jwtService.generateToken(userWithOrderedRoles)).thenReturn(TOKEN);

        // When
        LoginResponseDTO response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertArrayEquals(new String[]{"FIRST", "SECOND", "THIRD"}, response.getRoles());
    }
}