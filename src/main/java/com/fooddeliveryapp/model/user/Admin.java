package com.fooddeliveryapp.model.user;

import com.fooddeliveryapp.enums.Role;

public class Admin extends User{
    public Admin(String name, String email, String phone, String password, Role role) {
        super(name, email, phone, password,role);
    }
}
