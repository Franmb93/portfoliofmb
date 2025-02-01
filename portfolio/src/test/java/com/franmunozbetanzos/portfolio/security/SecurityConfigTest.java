package com.franmunozbetanzos.portfolio.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    void securityFilterChain_ShouldConfigureHttpSecurity() throws Exception {
        // Given
        HttpSecurity http = mock(HttpSecurity.class, invocation -> {
            if (invocation.getMethod()
                    .getName()
                    .equals("build")) {
                return mock(SecurityFilterChain.class);
            }
            return invocation.getMock();
        });

        // When
        SecurityFilterChain filterChain = securityConfig.securityFilterChain(http, jwtAuthenticationFilter);

        // Then
        assertNotNull(filterChain);
    }

    @Test
    void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
        // Given
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        // When
        AuthenticationManager result = securityConfig.authenticationManager(authenticationConfiguration);

        // Then
        assertNotNull(result);
        assertEquals(authenticationManager, result);
    }
}