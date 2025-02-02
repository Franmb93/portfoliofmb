package com.franmunozbetanzos.portfolio.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    String message() default "{validation.password.match}";
    String password() default "password";
    String confirmPassword() default "repeatPassword";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


