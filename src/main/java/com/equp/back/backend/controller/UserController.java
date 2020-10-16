package com.equp.back.backend.controller;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;
import com.equp.back.backend.service.UserService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.coyote.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;
    JSONObject responce = new JSONObject();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/v1/users")
    public ResponseEntity<?> create(@RequestParam (name = "username")String username, @RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password) {

        JSONObject responseObject = new JSONObject();

        List<User> users = userService.readAll();
        for (User us: users) {
            if (us.getEmail().equals(email)) {
                responseObject.put("status", 2);
                responseObject.put("message", "the user with email " + email + " already exists");
                responseObject.put("id", -1);
                System.out.printf(responseObject.toString());
                return new ResponseEntity<>(responseObject.toMap(), HttpStatus.CONFLICT);
            }
        }

        User user = new User();
        userService.create(username, email, password, user);
        responseObject.put("status",1);
        responseObject.put("message", "the user with email "+email+" created");
        responseObject.put("id",user.getId());
        System.out.printf(responseObject.toString());
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/v1/users")
    public ResponseEntity<?> read(@RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password){

        JSONObject responseObject = new JSONObject();

        User user = userService.read(email, password);
        if (user == null) {
            System.out.println(123456);
            responseObject.put("status",0);
            responseObject.put("message","Пользователь с такими email или паролем не найден");
            responseObject.put("id", -1);
            responseObject.put("name", "");
            responseObject.put("email", "");
            responseObject.put("experience", new Experience(-1));
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);
        }
        responseObject.put("status",1);
        responseObject.put("message","Пользователь найден");
        responseObject.put("id", user.getId());
        responseObject.put("name", user.getName());
        responseObject.put("email", user.getEmail());
        responseObject.put("experience", user.getExperience());
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);

    }

    @PostMapping(value = "/api/v1/users/delete")
    public ResponseEntity<?> delete(@RequestParam (name = "email")String email,
                                  @RequestParam (name = "password")String password){
        JSONObject responseObject = new JSONObject();
        byte status = 0;
        User user = userService.read(email, password);
        if (user == null) {
            responseObject.put("status",status);
            responseObject.put("message", "Пользователель с таким email и паролем не найден");
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);
        }


        if (userService.delete(user)) {
            status = 1;
            responseObject.put("status", status);
            responseObject.put("message", "пользователь удален");
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);

    }
}

