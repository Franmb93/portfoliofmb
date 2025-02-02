package com.franmunozbetanzos.portfolio.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Should validate when all fields are valid")
    void shouldValidateWhenAllFieldsAreValid() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .username("validUser")
                .password("Test1234@")
                .repeatPassword("Test1234@")
                .email("test@example.com")
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Nested
    @DisplayName("Username validation")
    class UsernameValidation {

        @Test
        @DisplayName("Should fail when username is blank")
        void shouldFailWhenUsernameIsBlank() {
            RegisterRequest request = RegisterRequest.builder()
                    .username("")
                    .password("Test1234@")
                    .repeatPassword("Test1234@")
                    .email("test@example.com")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.username.notblank}");
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab", "abcdefghijklmnopqrstu"})
        @DisplayName("Should fail when username length is invalid")
        void shouldFailWhenUsernameLengthIsInvalid(String username) {
            RegisterRequest request = RegisterRequest.builder()
                    .username(username)
                    .password("Test1234@")
                    .repeatPassword("Test1234@")
                    .email("test@example.com")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.username.size}");
        }

        @ParameterizedTest
        @ValueSource(strings = {"user name", "user$name", "user#name"})
        @DisplayName("Should fail when username contains invalid characters")
        void shouldFailWhenUsernameContainsInvalidCharacters(String username) {
            RegisterRequest request = RegisterRequest.builder()
                    .username(username)
                    .password("Test1234@")
                    .repeatPassword("Test1234@")
                    .email("test@example.com")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.username.pattern}");
        }
    }

    @Nested
    @DisplayName("Password validation")
    class PasswordValidation {

        private static Stream<String> invalidPasswords() {
            return Stream.of("Aa1@", "Aa1@" + "a".repeat(50));
        }

        @Test
        @DisplayName("Should fail when password is blank")
        void shouldFailWhenPasswordIsBlank() {
            RegisterRequest request = RegisterRequest.builder()
                    .username("validUser")
                    .password("")
                    .repeatPassword("")
                    .email("test@example.com")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.password.notblank}");
        }

        @ParameterizedTest
        @MethodSource("invalidPasswords")
        @DisplayName("Should fail when password length is invalid")
        void shouldFailWhenPasswordLengthIsInvalid(String password) {
            RegisterRequest request = RegisterRequest.builder()
                    .username("validUser")
                    .password(password)
                    .repeatPassword(password)
                    .email("test@example.com")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.password.size}");
        }

        @ParameterizedTest
        @ValueSource(strings = {"password", "Password", "Password1", "password1@", "PASSWORD1@"})
        @DisplayName("Should fail when password pattern is invalid")
        void shouldFailWhenPasswordPatternIsInvalid(String password) {
            RegisterRequest request = RegisterRequest.builder()
                    .username("validUser")
                    .password(password)
                    .repeatPassword(password)
                    .email("test@example.com")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.password.pattern}");
        }

        @Test
        @DisplayName("Should fail when passwords don't match")
        void shouldFailWhenPasswordsDontMatch() {
            RegisterRequest request = RegisterRequest.builder()
                    .username("validUser")
                    .password("Test1234@")
                    .repeatPassword("Test1234#")
                    .email("test@example.com")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.password.match}");
        }
    }

    @Nested
    @DisplayName("Email validation")
    class EmailValidation {

        @Test
        @DisplayName("Should fail when email is blank")
        void shouldFailWhenEmailIsBlank() {
            RegisterRequest request = RegisterRequest.builder()
                    .username("validUser")
                    .password("Test1234@")
                    .repeatPassword("Test1234@")
                    .email("")
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.email.notblank}");
        }

        @ParameterizedTest
        @ValueSource(strings = {"notanemail", "@nodomain.com", "no.at.symbol", "spaces in@email.com", "double..dots@email.com"})
        @DisplayName("Should fail when email format is invalid")
        void shouldFailWhenEmailFormatIsInvalid(String email) {
            RegisterRequest request = RegisterRequest.builder()
                    .username("validUser")
                    .password("Test1234@")
                    .repeatPassword("Test1234@")
                    .email(email)
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.email.invalid}");
        }

        @Test
        @DisplayName("Should fail when email is too long")
        void shouldFailWhenEmailIsTooLong() {
            String longEmail = "a".repeat(40) + "@" + "domain.com";

            RegisterRequest request = RegisterRequest.builder()
                    .username("validUser")
                    .password("Test1234@")
                    .repeatPassword("Test1234@")
                    .email(longEmail)
                    .build();

            Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("{validation.email.size}");
        }
    }
}