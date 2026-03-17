package com.fooddeliveryapp.model.user;

import com.fooddeliveryapp.enums.Role;

public class Customer extends User{
    public Customer(String name, String email, String phone, String password, Role role) {
        super(name,email,phone,password,role);
    }

    public Customer() {
        super();
    }



    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPhone() {
        return super.getPhone();
    }

    @Override
    public void setPhone(String phone) {
        super.setPhone(phone);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public int getCounter() {
        return super.getCounter();
    }

    @Override
    public void setCounter(int counter) {
        super.setCounter(counter);
    }

    @Override
    public Role getRole() {
        return super.getRole();
    }

    @Override
    public void setRole(Role role) {
        super.setRole(role);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }



    @Override
    public String toString() {
        return super.toString();
    }


}
