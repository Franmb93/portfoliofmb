package com.franmunozbetanzos.portfolio.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContactTest {

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(contact);
    }

    @Test
    void testNameField() {
        String name = "John Doe";
        contact.setName(name);
        assertEquals(name, contact.getName());
    }

    @Test
    void testEmailField() {
        String email = "john.doe@example.com";
        contact.setEmail(email);
        assertEquals(email, contact.getEmail());
    }

    @Test
    void testMessageField() {
        String message = "Hello World!";
        contact.setMessage(message);
        assertEquals(message, contact.getMessage());
    }

    @Test
    void testIdField() {
        Long id = 123L;
        contact.setId(id);
        assertEquals(id, contact.getId());
    }

    @Test
    void testToStringMethod() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String message = "Hello World!";

        contact.setName(name);
        contact.setEmail(email);
        contact.setMessage(message);

        String expectedToStringOutput = "Contact(super=BaseEntity(createdAt=null, updatedAt=null), id=" + null + ", name=" + name + ", email=" + email + ", message=" + message + ")";
        assertEquals(expectedToStringOutput, contact.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();

        contact1.setName("John Doe");
        contact2.setName("John Doe");

        contact1.setEmail("john.doe@example.com");
        contact2.setEmail("john.doe@example.com");

        contact1.setMessage("Hello World!");
        contact2.setMessage("Hello World!");

        assertEquals(contact1, contact2);
        assertEquals(contact1.hashCode(), contact2.hashCode());
    }
}
