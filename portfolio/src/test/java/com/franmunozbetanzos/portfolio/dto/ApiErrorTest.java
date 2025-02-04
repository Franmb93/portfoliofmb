package com.franmunozbetanzos.portfolio.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiErrorTest {

    @Test
    public void testAllArgsConstructor() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Error message";
        String path = "/api/test";

        ApiError apiError = new ApiError(status, timestamp, Collections.emptyList(), message, path);

        assertThat(apiError.getStatus()).isEqualTo(status);
        assertThat(apiError.getTimestamp()).isEqualTo(timestamp);
        assertThat(apiError.getMessage()).isEqualTo(message);
        assertThat(apiError.getPath()).isEqualTo(path);
    }

    @Test
    public void testBuilder() {
        HttpStatus status = HttpStatus.NOT_FOUND;
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Not found";
        String path = "/api/notfound";

        ApiError apiError = ApiError.builder()
                .status(status)
                .timestamp(timestamp)
                .message(message)
                .path(path)
                .build();

        assertThat(apiError.getStatus()).isEqualTo(status);
        assertThat(apiError.getTimestamp()).isEqualTo(timestamp);
        assertThat(apiError.getMessage()).isEqualTo(message);
        assertThat(apiError.getPath()).isEqualTo(path);
    }

    @Test
    public void testRequiredArgsConstructor() {
        ApiError apiError = new ApiError();

        assertThat(apiError.getStatus()).isNull();
        assertThat(apiError.getTimestamp()).isNull();
        assertThat(apiError.getMessage()).isNull();
        assertThat(apiError.getPath()).isNull();
    }
}