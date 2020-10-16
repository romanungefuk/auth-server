package com.equp.back.backend.service;

import com.equp.back.backend.exception.UserNotFound;
import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService{

    private static final Map<String, User> USER_REPOSITORY_MAP = new HashMap<>();

    private static final AtomicInteger USER_ID_HOLDER = new AtomicInteger();


    @Override
    public void create(String name, String email, String password, User user) {
        USER_REPOSITORY_MAP.put(email, user);
        final int userId = USER_ID_HOLDER.incrementAndGet();
        user.setId(userId);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setExperience(new Experience(userId));
        System.out.printf("created user: "+name+"\n");
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(USER_REPOSITORY_MAP.values());
    }

    @Override
    public User read(String userEmail, String password) {
        System.out.println("стартовал поиск "+USER_REPOSITORY_MAP.get(userEmail));
        try {
            if (USER_REPOSITORY_MAP.get(userEmail).getPassword().equals(password)){
                System.out.println("пользователь нашелся");
                return USER_REPOSITORY_MAP.get(userEmail);
            }
        }catch (NullPointerException e){
            System.out.println(e);
        }

            return null;

        }


    @Override
    public boolean update(User user, String userEmail, String newPassword) {

        try{
            if (USER_REPOSITORY_MAP.containsKey(userEmail)) {
                USER_REPOSITORY_MAP.get(userEmail).setPassword(newPassword);
                return true;
            }

        }catch (UserNotFound e){
            System.out.println(e);
        }

        return false;
    }

    @Override
    public boolean delete(String userEmail, String password)
    {
        return USER_REPOSITORY_MAP.remove(userEmail) != null;
    }

    @Override
    public boolean delete(User user)
    {
        return USER_REPOSITORY_MAP.remove(user.getEmail()) != null;
    }

}
