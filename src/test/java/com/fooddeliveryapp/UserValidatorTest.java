package com.fooddeliveryapp;

import com.fooddeliveryapp.validation.UserValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    // Name

    @Test
    void validName_shouldNotThrow() {
        assertDoesNotThrow(() -> UserValidator.validateName("Darshit"));
    }

    @Test
    void emptyName_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateName(""));
    }

    @Test
    void nullName_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateName(null));
    }

    // Email

    @Test
    void validEmail_shouldNotThrow() {
        assertDoesNotThrow(() -> UserValidator.validateEmail("test@gmail.com"));
    }

    @Test
    void emailWithoutAt_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateEmail("testgmail.com"));
    }

    @Test
    void emailWithoutDomain_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateEmail("test@gmail"));
    }

    @Test
    void nullEmail_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateEmail(null));
    }

    // Phone

    @Test
    void validPhone_shouldNotThrow() {
        assertDoesNotThrow(() -> UserValidator.validatePhone("9876543210"));
    }

    @Test
    void phoneStartingWith5_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validatePhone("5876543210"));
    }

    @Test
    void phoneLessThan10Digits_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validatePhone("987654321"));
    }

    @Test
    void phoneWithLetters_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validatePhone("98765432ab"));
    }

    // Password

    @Test
    void validPassword_shouldNotThrow() {
        assertDoesNotThrow(() -> UserValidator.validatePassword("admin@123"));
    }

    @Test
    void shortPassword_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validatePassword("abc"));
    }

    @Test
    void nullPassword_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validatePassword(null));
    }
}