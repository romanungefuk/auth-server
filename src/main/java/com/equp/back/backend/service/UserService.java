package com.equp.back.backend.service;

import com.equp.back.backend.model.User;

import java.util.List;

public interface UserService {
    public void create(String name, String email, String password, User user);

    List<User> readAll();

    User read (String email, String password);

    User findByEmail(String email);

    boolean update(User user, String userEmail, String newPassword);

    boolean delete(String userEmail, String password);

    boolean delete(User user);

}
