package com.franmunozbetanzos.portfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franmunozbetanzos.portfolio.dto.LoginRequestDTO;
import com.franmunozbetanzos.portfolio.dto.LoginResponseDTO;
import com.franmunozbetanzos.portfolio.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.franmunozbetanzos.portfolio.constant.ApiConstants.AUTH_PATH;
import static com.franmunozbetanzos.portfolio.constant.ApiConstants.LOGIN_PATH;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "testPass";
    private static final String TOKEN = "test.jwt.token";

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {
        // Given
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        LoginResponseDTO loginResponse = LoginResponseDTO.builder()
                .token(TOKEN)
                .username(USERNAME)
                .roles(new String[]{"USER"})
                .build();

        when(authService.login(any(LoginRequestDTO.class))).thenReturn(loginResponse);

        // When & Then
        mockMvc.perform(post(AUTH_PATH + LOGIN_PATH).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value(TOKEN))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.roles[0]").value("USER"));

        verify(authService).login(any(LoginRequestDTO.class));
    }

    @Test
    void login_ShouldReturn400_WhenUsernameIsEmpty() throws Exception {
        // Given
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .username("")
                .password(PASSWORD)
                .build();

        // When & Then
        mockMvc.perform(post(AUTH_PATH + LOGIN_PATH).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verifyNoInteractions(authService);
    }

    @Test
    void login_ShouldReturn400_WhenPasswordIsEmpty() throws Exception {
        // Given
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .username(USERNAME)
                .password("")
                .build();

        // When & Then
        mockMvc.perform(post(AUTH_PATH + LOGIN_PATH).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

        verifyNoInteractions(authService);
    }

    @Test
    void login_ShouldReturn400_WhenRequestBodyIsInvalid() throws Exception {
        // When & Then
        mockMvc.perform(post(AUTH_PATH + LOGIN_PATH).contentType(MediaType.APPLICATION_JSON)
                                .content("invalid json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(authService);
    }

    @Test
    void login_ShouldReturn415_WhenContentTypeIsNotJson() throws Exception {
        // Given
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        // When & Then
        mockMvc.perform(post(AUTH_PATH + LOGIN_PATH).contentType(MediaType.TEXT_PLAIN)
                                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnsupportedMediaType());

        verifyNoInteractions(authService);
    }
}