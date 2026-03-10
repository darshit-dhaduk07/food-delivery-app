package com.fooddeliveryapp.model.user;

abstract public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private int counter = 0;

    public User(String name, String email, String phone, String password) {
        this.id = ++counter;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
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
