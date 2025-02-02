package com.franmunozbetanzos.portfolio.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    private static final String TOKEN = "test.jwt.token";
    private static final String USERNAME = "testUser";
    private static final String[] ROLES = {"USER", "ADMIN"};

    @Test
    void builder_ShouldCreateObjectWithAllProperties() {
        // When
        LoginResponse response = LoginResponse.builder()
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
        LoginResponse response = new LoginResponse();

        // Then
        assertNull(response.getToken());
        assertNull(response.getUsername());
        assertNull(response.getRoles());
    }

    @Test
    void allArgsConstructor_ShouldCreateFullObject() {
        // When
        LoginResponse response = new LoginResponse(TOKEN, USERNAME, ROLES);

        // Then
        assertEquals(TOKEN, response.getToken());
        assertEquals(USERNAME, response.getUsername());
        assertArrayEquals(ROLES, response.getRoles());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        LoginResponse response = new LoginResponse();

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
        LoginResponse response1 = new LoginResponse(TOKEN, USERNAME, ROLES);
        LoginResponse response2 = new LoginResponse(TOKEN, USERNAME, ROLES);

        // Then
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void equals_ShouldReturnFalse_WhenObjectsAreDifferent() {
        // Given
        LoginResponse response1 = new LoginResponse(TOKEN, USERNAME, ROLES);
        LoginResponse response2 = new LoginResponse("different.token", USERNAME, ROLES);

        // Then
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        LoginResponse response = new LoginResponse(TOKEN, USERNAME, ROLES);

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
        LoginResponse original = new LoginResponse(TOKEN, USERNAME, ROLES);

        // When
        LoginResponse copy = LoginResponse.builder()
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
        LoginResponse response = new LoginResponse(null, null, null);

        // Then
        assertNull(response.getToken());
        assertNull(response.getUsername());
        assertNull(response.getRoles());
    }
}