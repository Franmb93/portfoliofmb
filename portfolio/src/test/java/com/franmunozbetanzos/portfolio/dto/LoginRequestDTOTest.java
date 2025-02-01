package com.franmunozbetanzos.portfolio.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDTOTest {

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "testPassword";
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void builder_ShouldCreateObjectWithAllProperties() {
        // When
        LoginRequestDTO request = LoginRequestDTO.builder()
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
        LoginRequestDTO request = new LoginRequestDTO();

        // Then
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }

    @Test
    void allArgsConstructor_ShouldCreateFullObject() {
        // When
        LoginRequestDTO request = new LoginRequestDTO(USERNAME, PASSWORD);

        // Then
        assertEquals(USERNAME, request.getUsername());
        assertEquals(PASSWORD, request.getPassword());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        LoginRequestDTO request = new LoginRequestDTO();

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
        LoginRequestDTO request1 = new LoginRequestDTO(USERNAME, PASSWORD);
        LoginRequestDTO request2 = new LoginRequestDTO(USERNAME, PASSWORD);

        // Then
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void equals_ShouldReturnFalse_WhenObjectsAreDifferent() {
        // Given
        LoginRequestDTO request1 = new LoginRequestDTO(USERNAME, PASSWORD);
        LoginRequestDTO request2 = new LoginRequestDTO("differentUser", PASSWORD);

        // Then
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void validation_ShouldFailWhenUsernameIsNull() {
        // Given
        LoginRequestDTO request = LoginRequestDTO.builder()
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenUsernameIsEmpty() {
        // Given
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("")
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenUsernameIsBlank() {
        // Given
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("   ")
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenPasswordIsNull() {
        // Given
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username(USERNAME)
                .build();

        // When
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenPasswordIsEmpty() {
        // Given
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username(USERNAME)
                .password("")
                .build();

        // When
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldFailWhenPasswordIsBlank() {
        // Given
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username(USERNAME)
                .password("   ")
                .build();

        // When
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator()
                .next()
                .getMessage());
    }

    @Test
    void validation_ShouldPassWithValidFields() {
        // Given
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        // When
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void toString_ShouldNotIncludePasswordValue() {
        // Given
        LoginRequestDTO request = new LoginRequestDTO(USERNAME, PASSWORD);

        // When
        String toString = request.toString();

        // Then
        assertTrue(toString.contains(USERNAME));
        assertFalse(toString.contains(PASSWORD));
    }
}