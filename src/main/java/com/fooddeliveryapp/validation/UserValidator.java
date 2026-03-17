package com.fooddeliveryapp.validation;

import com.fooddeliveryapp.model.user.User;

import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[6-9][0-9]{9}$");
    public static void validate(User user) {
        validateName(user.getName());
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        validatePassword(user.getPassword());
    }

    public static void validateName(String name) {

        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    public static void validateEmail(String email) {

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static void validatePassword(String password) {

        if(password == null || password.length() < 6) {
            throw new IllegalArgumentException(
                    "Password must be at least 6 characters"
            );
        }
    }
    public static void validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException(
                    "Invalid phone number. Must be 10 digits starting with 6-9");
        }
    }
}
