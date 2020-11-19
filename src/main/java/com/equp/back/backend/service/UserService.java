package com.equp.back.backend.service;

import com.equp.back.backend.model.User;

import java.util.List;

public interface UserService {
    void create(User user);

    List<User> readAll();

    User findById(Long id);

    boolean update(User user, String password);

    boolean updateName(User user, String newName);

    boolean delete(Long id);

    User findByEmail(String email);



}
