package com.example.chatapp.service;

import com.example.chatapp.model.User;

import java.util.*;

public class UserServiceImpl {
    private static UserServiceImpl ins;

    public synchronized static UserServiceImpl getIns() {
        if (ins == null) {
            ins = new UserServiceImpl();
        }
        return ins;
    }

    private Set<User> users;

    private UserServiceImpl() {
        users = new HashSet<>();

        users.add(new User("vanannek", User.ERole.SALESPERSON));
        users.add(new User("queanpham", User.ERole.CUSTOMER));
        users.add(new User("anhoang", User.ERole.CUSTOMER));
    }

    public Set<User> getUsers() {
        return users;
    }

    public int numberOfUsers() {
        return users.size();
    }

    public void register(User user) {
        users.add(user);
    }

    public void delete(String username) {
        users.removeIf(u -> u.getUsername().equals(username));
    }

    public User getByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
