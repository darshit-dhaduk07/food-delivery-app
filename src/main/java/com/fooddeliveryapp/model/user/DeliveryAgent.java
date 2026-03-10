package com.fooddeliveryapp.model.user;

public class DeliveryAgent extends User{
    boolean isAvailable;
    public DeliveryAgent(String name, String email, String phone, String password) {
        super(name, email, phone, password);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
