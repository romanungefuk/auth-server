package com.equp.back.backend.controller;

import com.equp.back.backend.model.User;
import com.equp.back.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestParam (name = "username")String username, @RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password) {

        List<User> users = userService.readAll();
        boolean b = false;
        for (User us: users){
            if (us.getEmail().equals(email)) b = true;
        }
        if (b){
            return new ResponseEntity<String>("the user with this email address is already registered",HttpStatus.CONFLICT);
        }

        User user = new User();
        userService.create(username, email, password, user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @GetMapping(value = "/read")
    public ResponseEntity<?> read(@RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password){

        User user = userService.read(email, password);
        if (user == null) {
            return new ResponseEntity<String>("User not found", HttpStatus.FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.FOUND);

    }


}
