package com.fooddeliveryapp.model.user;

import com.fooddeliveryapp.enums.Role;

public class Customer extends User{

    public Customer(String name, String email, String phone, String password, Role role) {
        super(name,email,phone,password,role);
    }
}
