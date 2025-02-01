package com.franmunozbetanzos.portfolio.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseDTOTest {

    private static final String TOKEN = "test.jwt.token";
    private static final String USERNAME = "testUser";
    private static final String[] ROLES = {"USER", "ADMIN"};

    @Test
    void builder_ShouldCreateObjectWithAllProperties() {
        // When
        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(TOKEN)
                .username(USERNAME)
                .roles(ROLES)
                .build();

        // Then
        assertEquals(TOKEN, response.getToken());
        assertEquals(USERNAME, response.getUsername());
        assertArrayEquals(ROLES, response.getRoles());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyObject() {
        // When
        LoginResponseDTO response = new LoginResponseDTO();

        // Then
        assertNull(response.getToken());
        assertNull(response.getUsername());
        assertNull(response.getRoles());
    }

    @Test
    void allArgsConstructor_ShouldCreateFullObject() {
        // When
        LoginResponseDTO response = new LoginResponseDTO(TOKEN, USERNAME, ROLES);

        // Then
        assertEquals(TOKEN, response.getToken());
        assertEquals(USERNAME, response.getUsername());
        assertArrayEquals(ROLES, response.getRoles());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        LoginResponseDTO response = new LoginResponseDTO();

        // When
        response.setToken(TOKEN);
        response.setUsername(USERNAME);
        response.setRoles(ROLES);

        // Then
        assertEquals(TOKEN, response.getToken());
        assertEquals(USERNAME, response.getUsername());
        assertArrayEquals(ROLES, response.getRoles());
    }

    @Test
    void equals_ShouldReturnTrue_WhenObjectsAreEqual() {
        // Given
        LoginResponseDTO response1 = new LoginResponseDTO(TOKEN, USERNAME, ROLES);
        LoginResponseDTO response2 = new LoginResponseDTO(TOKEN, USERNAME, ROLES);

        // Then
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void equals_ShouldReturnFalse_WhenObjectsAreDifferent() {
        // Given
        LoginResponseDTO response1 = new LoginResponseDTO(TOKEN, USERNAME, ROLES);
        LoginResponseDTO response2 = new LoginResponseDTO("different.token", USERNAME, ROLES);

        // Then
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        LoginResponseDTO response = new LoginResponseDTO(TOKEN, USERNAME, ROLES);

        // When
        String toString = response.toString();

        // Then
        assertTrue(toString.contains(TOKEN));
        assertTrue(toString.contains(USERNAME));
        assertTrue(toString.contains(ROLES[0]));
        assertTrue(toString.contains(ROLES[1]));
    }

    @Test
    void deepCopy_ShouldCreateIndependentCopy() {
        // Given
        LoginResponseDTO original = new LoginResponseDTO(TOKEN, USERNAME, ROLES);

        // When
        LoginResponseDTO copy = LoginResponseDTO.builder()
                .token(original.getToken())
                .username(original.getUsername())
                .roles(original.getRoles().clone())
                .build();

        // Modify the original
        original.getRoles()[0] = "MODIFIED";

        // Then
        assertEquals("USER", copy.getRoles()[0], "Copy should not be affected by changes to original");
    }

    @Test
    void withNullValues_ShouldHandleNullsGracefully() {
        // Given
        LoginResponseDTO response = new LoginResponseDTO(null, null, null);

        // Then
        assertNull(response.getToken());
        assertNull(response.getUsername());
        assertNull(response.getRoles());
    }
}