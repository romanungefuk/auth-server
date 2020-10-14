package com.equp.backendequp.service;

import com.equp.backendequp.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findByEmail(String username);

    User findById(Long id);

    void delete(Long id);
}
