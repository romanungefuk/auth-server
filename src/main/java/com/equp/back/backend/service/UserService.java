package com.equp.back.backend.service;

import com.equp.back.backend.model.User;

import java.util.List;

public interface UserService {
    void create(User user);

    List<User> readAll();

    User findById(Long id);

    boolean update(User user, Long id);

    boolean delete(Long id);

    User findByEmail(String email);



}
