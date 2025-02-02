package com.franmunozbetanzos.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "{validation.login.username.notblank}")
    private String username;

    @NotBlank(message = "{validation.login.password.notblank}")
    private String password;

    @Override
    public String toString() {
        return "LoginRequestDTO{" + "username='" + username + '\'' + '}';
    }
}