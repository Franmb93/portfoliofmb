package com.franmunozbetanzos.portfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRegisterRequestException extends ResponseStatusException {
    public BadRegisterRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}