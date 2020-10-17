package com.equp.back.backend.service.impl;

import com.equp.back.backend.exception.UserNotFound;
import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;
import com.equp.back.backend.repository.UserRepository;
import com.equp.back.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
        }


    @Override
    public boolean update(User user, String newPassword) {
        if (userRepository.existsById(user.getId())) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByEmail(String email){

        return userRepository.findByEmail(email);
    }

}
