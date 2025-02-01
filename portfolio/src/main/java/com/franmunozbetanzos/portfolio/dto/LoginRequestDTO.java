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
public class LoginRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Username is required")
    private String password;

    @Override
    public String toString() {
        return "LoginRequestDTO{" + "username='" + username + '\'' + '}';
    }
}
