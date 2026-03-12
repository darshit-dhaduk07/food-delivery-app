package com.fooddeliveryapp.model.user;

import com.fooddeliveryapp.enums.Role;

abstract public class User {
//    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private int counter = 0;
    private Role role;

    public User(String name, String email, String phone, String password,Role role) {
//        this.id = ++counter;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

//    public int getId() {
//        return id;
//    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
