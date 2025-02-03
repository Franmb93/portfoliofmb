package com.franmunozbetanzos.portfolio.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;
    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.password();
        this.confirmPasswordField = constraintAnnotation.confirmPassword();
        this.message = constraintAnnotation.message();
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

            if (password == null || !password.equals(confirmPassword)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(confirmPasswordField)
                        .addConstraintViolation();
                return false;
            }

            return true;

        } catch (Exception e) {
            log.debug("Error al validar la coincidencia de contraseñas", e);
            return false;
        }
    }
}
