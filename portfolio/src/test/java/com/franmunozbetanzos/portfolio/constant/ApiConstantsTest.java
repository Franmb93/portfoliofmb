package com.franmunozbetanzos.portfolio.constant;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiConstantsTest {

    @Test
    void testApiBasePath() {
        assertEquals("/api/v1", ApiConstants.API_BASE_PATH);
    }

    @Test
    void testAuthPath() {
        assertEquals("/api/v1/auth", ApiConstants.AUTH_PATH);
    }

    @Test
    void testPublicPath() {
        assertEquals("/api/v1/public", ApiConstants.PUBLIC_PATH);
    }

    @Test
    void testAllPath() {
        assertEquals("/**", ApiConstants.ALL_PATH);
    }

    @Test
    void testJwtSecretKey() {
        assertEquals("${jwt.secret:defaultSecretKey}", ApiConstants.JWT_SECRET_KEY);
    }

    @Test
    void testJwtExpirationTime() {
        assertEquals(86400000, ApiConstants.JWT_EXPIRATION_TIME);
    }

    @Test
    void testJwtTokenPrefix() {
        assertEquals("Bearer ", ApiConstants.JWT_TOKEN_PREFIX);
    }

    @Test
    void testJwtHeaderString() {
        assertEquals("Authorization", ApiConstants.JWT_HEADER_STRING);
    }
}