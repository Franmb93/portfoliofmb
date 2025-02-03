package com.franmunozbetanzos.portfolio.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "testPassword";
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            MessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("messages"));

            validator = factory.usingContext()
                    .messageInterpolator(messageInterpolator)
                    .getValidator();
        }
    }

    @Test
    void builder_ShouldCreateObjectWithAllProperties() {
        // When
        LoginRequest request = LoginRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        // Then
        assertEquals(USERNAME, request.getUsername());
        assertEquals(PASSWORD, request.getPassword());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyObject() {
        // When
        LoginRequest request = new LoginRequest();

        // Then
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }

    @Test
    void allArgsConstructor_ShouldCreateFullObject() {
        // When
        LoginRequest request = new LoginRequest(USERNAME, PASSWORD);

        // Then
        assertEquals(USERNAME, request.getUsername());
        assertEquals(PASSWORD, request.getPassword());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        LoginRequest request = new LoginRequest();

        // When
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);

        // Then
        assertEquals(USERNAME, request.getUsername());
        assertEquals(PASSWORD, request.getPassword());
    }

    @Test
    void equals_ShouldReturnTrue_WhenObjectsAreEqual() {
        // Given
        LoginRequest request1 = new LoginRequest(USERNAME, PASSWORD);
        LoginRequest request2 = new LoginRequest(USERNAME, PASSWORD);

        // Then
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void equals_ShouldReturnFalse_WhenObjectsAreDifferent() {
        // Given
        LoginRequest request1 = new LoginRequest(USERNAME, PASSWORD);
        LoginRequest request2 = new LoginRequest("differentUser", PASSWORD);

        // Then
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void validation_ShouldFailWhenUsernameIsNull() {
        // Given
        LoginRequest request = LoginRequest.builder()
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El nombre de usuario es obligatorio", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenUsernameIsEmpty() {
        // Given
        LoginRequest request = LoginRequest.builder()
                .username("")
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El nombre de usuario es obligatorio", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenUsernameIsBlank() {
        // Given
        LoginRequest request = LoginRequest.builder()
                .username("   ")
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El nombre de usuario es obligatorio", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenPasswordIsNull() {
        // Given
        LoginRequest request = LoginRequest.builder()
                .username(USERNAME)
                .build();

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La contrasena es obligatoria", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenPasswordIsEmpty() {
        // Given
        LoginRequest request = LoginRequest.builder()
                .username(USERNAME)
                .password("")
                .build();

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La contrasena es obligatoria", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenPasswordIsBlank() {
        // Given
        LoginRequest request = LoginRequest.builder()
                .username(USERNAME)
                .password("   ")
                .build();

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La contrasena es obligatoria", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldPassWithValidFields() {
        // Given
        LoginRequest request = LoginRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void toString_ShouldNotIncludePasswordValue() {
        // Given
        LoginRequest request = new LoginRequest(USERNAME, PASSWORD);

        // When
        String toString = request.toString();

        // Then
        assertTrue(toString.contains(USERNAME));
        assertFalse(toString.contains(PASSWORD));
    }
}