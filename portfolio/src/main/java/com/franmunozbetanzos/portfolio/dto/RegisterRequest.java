package com.franmunozbetanzos.portfolio.dto;

import com.franmunozbetanzos.portfolio.constant.ValidationConstants;
import com.franmunozbetanzos.portfolio.validator.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@PasswordMatch
public class RegisterRequest {

    @NotBlank(message = "{validation.username.notblank}")
    @Size(min = ValidationConstants.Username.MIN_LENGTH, max = ValidationConstants.Username.MAX_LENGTH, message = "{validation.username.size}")
    @Pattern(regexp = ValidationConstants.Username.PATTERN, message = "{validation.username.pattern}")
    private String username;

    @NotBlank(message = "{validation.password.notblank}")
    @Size(min = ValidationConstants.Password.MIN_LENGTH, max = ValidationConstants.Password.MAX_LENGTH, message = "{validation.password.size}")
    @Pattern(regexp = ValidationConstants.Password.PATTERN, message = "{validation.password.pattern}")
    private String password;

    @NotBlank(message = "{validation.repeatPassword.notblank}")
    private String repeatPassword;

    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.invalid}")
    @Size(max = ValidationConstants.Email.MAX_LENGTH, message = "{validation.email.size}")
    private String email;
}
