package com.equp.back.backend.controller;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    JSONObject responce = new JSONObject();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/v1/signup")
    public ResponseEntity<?> create(@RequestParam (name = "username")String username, @RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password) {

        JSONObject responseObject = new JSONObject();

        List<User> users = userService.readAll();
        for (User us: users) {
            if (us.getEmail().equals(email)) {
                responseObject.put("status", 412);
                responseObject.put("message", "Пользователь с email: " + email + " создан");
                responseObject.put("id", -1);
                System.out.printf(responseObject.toString());
                log.info(responseObject.toString());
                return new ResponseEntity<>(responseObject.toMap(), HttpStatus.PRECONDITION_FAILED);
            }
        }

        User user = new User();
        userService.create(username, email, password, user);
        responseObject.put("status",201);
        responseObject.put("message", "the user with email "+email+" created");
        responseObject.put("id",user.getId());
        System.out.printf(responseObject.toString());
        log.info(responseObject.toString());
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/v1/auth")
    public ResponseEntity<?> read(@RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password){

        JSONObject responseObject = new JSONObject();

        User user = userService.read(email, password);
        if (user == null) {

            responseObject.put("status",404);
            responseObject.put("message","Пользователь с такими email или паролем не найден");
            responseObject.put("id", -1);
            responseObject.put("name", "");
            responseObject.put("email", "");
            responseObject.put("experience", new Experience(-1));
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }
        responseObject.put("status",302);
        responseObject.put("message","Пользователь найден");
        responseObject.put("id", user.getId());
        responseObject.put("name", user.getName());
        responseObject.put("email", user.getEmail());
        responseObject.put("experience", user.getExperience());
        log.info(responseObject.toString());
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);

    }

    @PostMapping(value = "/api/v1/delete")
    public ResponseEntity<?> delete(@RequestParam (name = "email")String email,
                                  @RequestParam (name = "password")String password){
        JSONObject responseObject = new JSONObject();
        byte status = 0;
        User user = userService.read(email, password);
        if (user == null) {
            responseObject.put("status",404);
            responseObject.put("message","Пользователь с такими email или паролем не найден");
            responseObject.put("id", -1);
            responseObject.put("name", "");
            responseObject.put("email", "");
            responseObject.put("experience", new Experience(-1));
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }


        if (userService.delete(user)) {
            responseObject.put("status",202);
            responseObject.put("message","Пользователь удален");
            responseObject.put("id", -1);
            responseObject.put("name", "");
            responseObject.put("email", "");
            responseObject.put("experience", new Experience(-1));
            log.info(responseObject.toString());
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }

    @PostMapping(value = "/api/v1/update")
    public ResponseEntity<?> delete(@RequestParam (name = "email")String email){
        JSONObject responseObject = new JSONObject();
        String tempEmail;
        User user = userService.findByEmail(email);
        if (user == null) {
            responseObject.put("status", 404);
            responseObject.put("message","Пользователь с такими email или паролем не найден");
            responseObject.put("id", -1);
            responseObject.put("name", "");
            responseObject.put("email", "");
            responseObject.put("experience", new Experience(-1));
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }

            responseObject.put("status",202);
            responseObject.put("message","На email: "+email+" отправлена информация о изменении пароля");
            responseObject.put("id", -1);
            responseObject.put("name", "");
            responseObject.put("email", "");
            responseObject.put("experience", new Experience(-1));
            user.setPassword("111");
            log.info(responseObject.toString()+"set password: 111");

        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }

}

