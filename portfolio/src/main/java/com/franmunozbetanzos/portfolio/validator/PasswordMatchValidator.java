package com.franmunozbetanzos.portfolio.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.password();
        this.confirmPasswordField = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field passwordFld = value.getClass()
                    .getDeclaredField(passwordField);
            Field confirmPasswordFld = value.getClass()
                    .getDeclaredField(confirmPasswordField);

            passwordFld.setAccessible(true);
            confirmPasswordFld.setAccessible(true);

            String password = (String) passwordFld.get(value);
            String confirmPassword = (String) confirmPasswordFld.get(value);

            if (password == null || confirmPassword == null) {
                return false;
            }

            return password.equals(confirmPassword);

        } catch (Exception e) {
            log.debug("Las contraseñas no coinciden y no se ha podido validar");
            return false;
        }
    }
}