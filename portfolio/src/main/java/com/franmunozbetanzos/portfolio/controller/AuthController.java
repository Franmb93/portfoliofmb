package com.franmunozbetanzos.portfolio.controller;

import com.franmunozbetanzos.portfolio.dto.LoginRequest;
import com.franmunozbetanzos.portfolio.dto.LoginResponse;
import com.franmunozbetanzos.portfolio.dto.RegisterRequest;
import com.franmunozbetanzos.portfolio.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.franmunozbetanzos.portfolio.constant.ApiConstants.*;

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
     * @param request the login credentials
     * @return JWT token and user information if authentication is successful
     */
    @PostMapping(LOGIN_PATH)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }



    @PostMapping(REGISTER_PATH)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {

        return ResponseEntity.ok()
                .build();
    }
}