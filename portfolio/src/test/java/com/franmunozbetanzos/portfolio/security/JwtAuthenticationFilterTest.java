package com.franmunozbetanzos.portfolio.security;

import com.franmunozbetanzos.portfolio.constant.ApiConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    private static final String VALID_TOKEN = "valid.jwt.token";
    private static final String VALID_EMAIL = "test@example.com";
    @Mock
    private JwtService jwtService;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private UserDetails userDetails;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @Test
    void whenNoAuthHeader_thenContinueWithFilterChain() throws ServletException, IOException {
        // Given
        when(request.getHeader(ApiConstants.JWT_HEADER_STRING)).thenReturn(null);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService, userDetailsService);
    }

    @Test
    void whenInvalidAuthHeaderFormat_thenContinueWithFilterChain() throws ServletException, IOException {
        // Given
        when(request.getHeader(ApiConstants.JWT_HEADER_STRING)).thenReturn("InvalidFormat Token");

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService, userDetailsService);
    }

    @Test
    void whenValidToken_thenAuthenticateUser() throws ServletException, IOException {
        // Given
        String authHeader = ApiConstants.JWT_TOKEN_PREFIX + VALID_TOKEN;
        when(request.getHeader(ApiConstants.JWT_HEADER_STRING)).thenReturn(authHeader);
        when(jwtService.extractUsername(VALID_TOKEN)).thenReturn(VALID_EMAIL);
        when(userDetailsService.loadUserByUsername(VALID_EMAIL)).thenReturn(userDetails);
        when(jwtService.isTokenValid(VALID_TOKEN, userDetails)).thenReturn(true);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(jwtService).extractUsername(VALID_TOKEN);
        verify(userDetailsService).loadUserByUsername(VALID_EMAIL);
        verify(jwtService).isTokenValid(VALID_TOKEN, userDetails);
        assert SecurityContextHolder.getContext()
                .getAuthentication() != null;
    }

    @Test
    void whenInvalidToken_thenDontAuthenticate() throws ServletException, IOException {
        // Given
        String authHeader = ApiConstants.JWT_TOKEN_PREFIX + VALID_TOKEN;
        when(request.getHeader(ApiConstants.JWT_HEADER_STRING)).thenReturn(authHeader);
        when(jwtService.extractUsername(VALID_TOKEN)).thenReturn(VALID_EMAIL);
        when(userDetailsService.loadUserByUsername(VALID_EMAIL)).thenReturn(userDetails);
        when(jwtService.isTokenValid(VALID_TOKEN, userDetails)).thenReturn(false);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(jwtService).isTokenValid(VALID_TOKEN, userDetails);
        assert SecurityContextHolder.getContext()
                .getAuthentication() == null;
    }

    @Test
    void whenNullUsername_thenDontAuthenticate() throws ServletException, IOException {
        // Given
        String authHeader = ApiConstants.JWT_TOKEN_PREFIX + VALID_TOKEN;
        when(request.getHeader(ApiConstants.JWT_HEADER_STRING)).thenReturn(authHeader);
        when(jwtService.extractUsername(VALID_TOKEN)).thenReturn(null);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(jwtService).extractUsername(VALID_TOKEN);
        verifyNoInteractions(userDetailsService);
        assert SecurityContextHolder.getContext()
                .getAuthentication() == null;
    }
}