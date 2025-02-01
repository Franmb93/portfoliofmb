package com.franmunozbetanzos.portfolio.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableConfigurationProperties(JwtProperties.class)
@TestPropertySource(properties = {
        "jwt.secret=testSecretKey123456789",
        "jwt.expiration=3600000"
})
class JwtPropertiesTest {

    @Configuration
    @EnableConfigurationProperties(JwtProperties.class)
    static class TestConfig {
    }

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void whenPropertiesSet_thenLoadCorrectly() {
        // When properties are set in @TestPropertySource

        // Then
        assertNotNull(jwtProperties, "JwtProperties should not be null");
        assertEquals("testSecretKey123456789", jwtProperties.getSecret(), "Secret should match the configured value");
        assertEquals(3600000L, jwtProperties.getExpiration(), "Expiration should match the configured value");
    }

    @Test
    void whenSetProperties_thenGetCorrectValues() {
        // Given
        JwtProperties properties = new JwtProperties();
        String secret = "newTestSecret";
        long expiration = 7200000L;

        // When
        properties.setSecret(secret);
        properties.setExpiration(expiration);

        // Then
        assertEquals(secret, properties.getSecret(), "Secret should match the set value");
        assertEquals(expiration, properties.getExpiration(), "Expiration should match the set value");
    }
}