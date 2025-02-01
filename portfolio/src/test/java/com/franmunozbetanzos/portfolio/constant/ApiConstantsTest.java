package com.franmunozbetanzos.portfolio.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiConstantsTest {
    @Test
    void testConstructorIsPrivate() {
        assertThrows(IllegalAccessException.class, () -> ApiConstants.class.getDeclaredConstructor()
                .newInstance());
    }
}