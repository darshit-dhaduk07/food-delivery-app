package com.fooddeliveryapp.controller;

import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.User;
import com.fooddeliveryapp.service.AuthService;

public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    public Customer registerCustomer(String name, String email,
                                     String phone, String password) {
        return authService.registerCustomer(name, email, phone, password);
    }

    public User login(String email, String password) {
        return authService.login(email, password);
    }
}