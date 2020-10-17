package com.equp.back.backend.controller;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;
import com.equp.back.backend.repository.UserRepository;
import com.equp.back.backend.service.UserService;
import com.equp.back.backend.service.impl.EmailSendService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;


@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailSendService emailSendService;
    JSONObject responce = new JSONObject();

    @Autowired
    public UserController(UserService userService, EmailSendService emailSendService) {
        this.userService = userService;
        this.emailSendService = emailSendService;
    }

    @PostMapping(value = "/api/v1/signup")
    public ResponseEntity<?> signup(@RequestParam (name = "username")String username, @RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password) {


        JSONObject responseObject = new JSONObject();

            log.info("Старт поиска по email");
            boolean b = userService.findByEmail(email)!=null;
            log.info("конец поиска по email");
            if (b) {

                responseObject.put("status", 412);
                responseObject.put("message", "Пользователь с email: " + email + " уже существует");
                responseObject.put("id", -1);
                System.out.printf(responseObject.toString());
                log.info(responseObject.toString());
                return new ResponseEntity<>(responseObject.toMap(), HttpStatus.PRECONDITION_FAILED);
            }


        User user = new User(username,email,password);
        userService.create(user);
        responseObject.put("status",201);
        responseObject.put("message", "пользователь с email "+email+" создан");
        responseObject.put("id",user.getId());
        System.out.printf(responseObject.toString());
        log.info(responseObject.toString());
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/v1/auth")
    public ResponseEntity<?> read(@RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password){

        JSONObject responseObject = new JSONObject();

        User user = userService.findByEmail(email);
        if (user == null) {

            responseObject.put("status",404);
            responseObject.put("message","Пользователь с такими email не найден");
            responseObject.put("id", -1);

            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else if (user != null && !user.getPassword().equals(password)){
            responseObject.put("status",401);
            responseObject.put("message","Не верный пароль");
            responseObject.put("id", -1);

            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.UNAUTHORIZED);
        }else {
            responseObject.put("status",302);
            responseObject.put("message","Пользователь найден");
            responseObject.put("id", user.getId());
            responseObject.put("name", "");
            responseObject.put("email", "");
            responseObject.put("experience", new Experience(-user.getId()));
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);
        }

    }

    @PostMapping(value = "/api/v1/update")
    public ResponseEntity<?> delete(@RequestParam (name = "email")String email){
        JSONObject responseObject = new JSONObject();

        User user = userService.findByEmail(email);
        if (user == null) {
            responseObject.put("status", 404);
            responseObject.put("message", "Пользователь с такими email не найден");
            responseObject.put("id", -1);
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else {
            responseObject.put("status",202);
            responseObject.put("message","На email: "+email+" отправлена информация о изменении пароля");
            responseObject.put("id", -1L);
            user.setPassword("111");
            emailSendService.sendMessage(email);

            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);
        }

    }

    @PostMapping(value = "/api/v1/delete")
    public ResponseEntity<?> delete(@RequestParam (name = "email")String email,
                                  @RequestParam (name = "password")String password){
        JSONObject responseObject = new JSONObject();
        User user = userService.findByEmail(email);
        if (user == null) {
            responseObject.put("status", 404);
            responseObject.put("message", "Пользователь с такими email не найден");
            responseObject.put("id", -1);
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else if (user != null && !user.getPassword().equals(password)) {
            responseObject.put("status", 401);
            responseObject.put("message", "Не верный пароль");
            responseObject.put("id", -1);

            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.UNAUTHORIZED);
        }else{
            userService.delete(user.getId());
            responseObject.put("status",202);
            responseObject.put("message","Пользователь удален");
            responseObject.put("id", -1L);
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }


}

