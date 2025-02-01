package com.franmunozbetanzos.portfolio.controller;

import com.franmunozbetanzos.portfolio.dto.LoginRequestDTO;
import com.franmunozbetanzos.portfolio.dto.LoginResponseDTO;
import com.franmunozbetanzos.portfolio.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.franmunozbetanzos.portfolio.constant.ApiConstants.AUTH_PATH;
import static com.franmunozbetanzos.portfolio.constant.ApiConstants.LOGIN_PATH;

/**
 * Controller responsible for handling authentication-related endpoints.
 */
@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user login requests.
     *
     * @param LoginRequestDTO the login credentials
     * @return JWT token and user information if authentication is successful
     */
    @PostMapping(LOGIN_PATH)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}