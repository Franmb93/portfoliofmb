package com.franmunozbetanzos.portfolio.exception;

import com.franmunozbetanzos.portfolio.dto.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        MessageSource messageSource = mock(MessageSource.class);
        webRequest = mock(WebRequest.class);
        exceptionHandler = new GlobalExceptionHandler(messageSource);

        when(webRequest.getDescription(false)).thenReturn("uri=/test");
        when(messageSource.getMessage(eq("error.internal"), any(), any()))
                .thenReturn("Error interno mock");
    }

    @Test
    void handleConflict_ShouldReturnBadRequest() {
        // Given
        String errorMessage = "Invalid input";
        IllegalArgumentException ex = new IllegalArgumentException(errorMessage);

        // When
        ResponseEntity<Object> response = exceptionHandler.handleConflict(ex, webRequest);
        ApiError error = (ApiError) response.getBody();

        // Then
        assertNotNull(error);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, error.getMessage());
        assertEquals("uri=/test", error.getPath());
        assertNotNull(error.getTimestamp());
    }

    @Test
    void handleAllUncaught_ShouldReturnInternalServerError() {
        // When
        ResponseEntity<Object> response = exceptionHandler.handleAllUncaught(webRequest);
        ApiError error = (ApiError) response.getBody();

        // Then
        assertNotNull(error);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno mock", error.getMessage());
        assertEquals("uri=/test", error.getPath());
        assertNotNull(error.getTimestamp());
    }
}