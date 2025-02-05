package com.franmunozbetanzos.portfolio.exception;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BadRegisterRequestExceptionTest {

    @Test
    void BadRegisterRequestException_ShouldReturnProvidedReason() {
        String reason = "Invalid registration data";
        BadRegisterRequestException exception = new BadRegisterRequestException(reason);
        assertEquals(reason, exception.getReason());
    }

    @Test
    void BadRegisterRequestException_ShouldThrowResponseStatusException() {
        assertThrows(ResponseStatusException.class, () -> {
            throw new BadRegisterRequestException("Invalid registration data");
        });
    }
}