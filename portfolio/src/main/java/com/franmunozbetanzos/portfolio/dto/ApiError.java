package com.franmunozbetanzos.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private List<String> errors;
    private String message;
    private String path;
}
