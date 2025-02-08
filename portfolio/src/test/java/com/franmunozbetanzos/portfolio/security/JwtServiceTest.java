package com.franmunozbetanzos.portfolio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        jwtProperties = new JwtProperties();
        jwtProperties.setSecret("VGhpc0lzQVZlcnlTZWN1cmVBbmRMb25nU2VjcmV0S2V5Rm9yVGhlSldUVG9rZW5HZW5lcmF0aW9u");
        jwtProperties.setExpiration(7200000);
        jwtService = new JwtService(jwtProperties);
    }

    @Test
    void testGenerateToken() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("user", username);
    }

    @Test
    void testIsTokenValid() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenExpired() throws Exception {
        String token = createExpiredToken();
        boolean result = invokeIsTokenExpired(token);
        assertTrue(result);
    }

    private String createExpiredToken() {
        return Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean invokeIsTokenExpired(String token) throws Exception {
        java.lang.reflect.Method method = JwtService.class.getDeclaredMethod("isTokenExpired", String.class);
        method.setAccessible(true);
        try {
            return (boolean) method.invoke(jwtService, token);
        } catch (Exception e) {
            if (e.getCause() instanceof io.jsonwebtoken.ExpiredJwtException) {
                return true;
            } else {
                throw e;
            }
        }
    }


    @Test
    public void testExtractClaimWithReflection() throws Exception {
        JwtService jwtService = new JwtService(jwtProperties);

        String token = jwtService.generateToken(User.builder()
                                                        .username("user")
                                                        .password("password")
                                                        .roles("USER")
                                                        .build());

        Method method = JwtService.class.getDeclaredMethod("extractClaim", String.class, Function.class);
        method.setAccessible(true);

        // Accede al método privado usando ReflectionTestUtils
        String extractedUsername = (String) ReflectionTestUtils.invokeMethod(jwtService, "extractClaim", token, (Function<Claims, String>) Claims::getSubject);

        assertEquals("user", extractedUsername);
    }



}