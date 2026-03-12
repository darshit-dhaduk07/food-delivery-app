package com.fooddeliveryapp.model.user;

import com.fooddeliveryapp.enums.Role;

public class DeliveryAgent extends User{
    boolean isAvailable;
    public DeliveryAgent(String name, String email, String phone, String password, Role role) {
        super(name, email, phone, password,role);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
