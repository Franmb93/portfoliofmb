package com.franmunozbetanzos.portfolio.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PasswordMatchValidatorTest {

    private PasswordMatchValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new PasswordMatchValidator();
        PasswordMatch passwordMatch = createPasswordMatchAnnotation("password", "confirmPassword");
        context = mock(ConstraintValidatorContext.class);
        validator.initialize(passwordMatch);
    }

    @Test
    @DisplayName("Should return true when passwords match")
    void shouldReturnTrueWhenPasswordsMatch() {
        TestClass testObj = new TestClass("password123", "password123");

        boolean result = validator.isValid(testObj, context);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should return false when passwords don't match")
    void shouldReturnFalseWhenPasswordsDontMatch() {
        TestClass testObj = new TestClass("password123", "differentPassword");

        boolean result = validator.isValid(testObj, context);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when password is null")
    void shouldReturnFalseWhenPasswordIsNull() {
        TestClass testObj = new TestClass(null, "password123");

        boolean result = validator.isValid(testObj, context);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when confirm password is null")
    void shouldReturnFalseWhenConfirmPasswordIsNull() {
        TestClass testObj = new TestClass("password123", null);

        boolean result = validator.isValid(testObj, context);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when both passwords are null")
    void shouldReturnFalseWhenBothPasswordsAreNull() {
        TestClass testObj = new TestClass(null, null);

        boolean result = validator.isValid(testObj, context);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when field names don't exist")
    void shouldReturnFalseWhenFieldNamesDontExist() {
        validator.initialize(createPasswordMatchAnnotation("nonexistentField1", "nonexistentField2"));
        TestClass testObj = new TestClass("password123", "password123");

        boolean result = validator.isValid(testObj, context);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false for invalid object type")
    void shouldReturnFalseForInvalidObjectType() {
        String invalidObject = "not a valid object";

        boolean result = validator.isValid(invalidObject, context);

        assertThat(result).isFalse();
    }

    private PasswordMatch createPasswordMatchAnnotation(String password, String confirmPassword) {
        return new PasswordMatch() {
            @Override
            public String message() {
                return "{validation.password.match}";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @SuppressWarnings("unchecked")
            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return (Class<? extends jakarta.validation.Payload>[]) new Class[0];
            }

            @Override
            public String password() {
                return password;
            }

            @Override
            public String confirmPassword() {
                return confirmPassword;
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return PasswordMatch.class;
            }
        };
    }

    private record TestClass(String password, String confirmPassword) {
    }
}