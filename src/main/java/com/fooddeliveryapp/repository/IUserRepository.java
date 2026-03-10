package com.fooddeliveryapp.repository;

import com.fooddeliveryapp.model.user.User;

import java.util.List;

public interface IUserRepository {
    User findUserById(int id);
    User findUserByEmail(String email);
    List<User> getAllUser();
    void addUser(User user);
    void removeUser(int id);
}
