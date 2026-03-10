package com.fooddeliveryapp.repository.impl;

import com.fooddeliveryapp.model.user.User;
import com.fooddeliveryapp.repository.IUserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImp implements IUserRepository {
    private final Map<Integer,User> userMap;
    private final Map<String,Integer> emailMap;
    private static UserRepositoryImp userRepositoryImp = null;
    private UserRepositoryImp()
    {
        userMap = new HashMap<>();
        emailMap = new HashMap<>();
    }
    public static UserRepositoryImp getInstance()
    {
        if(userRepositoryImp == null)
        {
            userRepositoryImp = new UserRepositoryImp();
        }
        return userRepositoryImp;
    }

    @Override
    public User findUserById(int id) {
        return userMap.get(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMap.get(emailMap.get(email));
    }

    @Override
    public List<User> getAllUser() {
        return userMap.values().stream().toList();
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getId(),user);
        emailMap.put(user.getEmail(), user.getId());
    }

    @Override
    public void removeUser(int id) {
        emailMap.remove(userMap.get(id).getEmail());
        userMap.remove(id);
    }
}
