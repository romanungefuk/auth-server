package com.equp.back.backend.controller;


import com.equp.back.backend.model.User;
import com.equp.back.backend.security.jwt.JwtTokenProvider;
import com.equp.back.backend.service.ExperienceService;
import com.equp.back.backend.service.TestresultService;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for authentication requests (login, logout, register, etc.)
 *
 * @author Roman Ungefuk
 * @version 1.0
 */

@RestController
@Slf4j
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final ExperienceService experienceService;

    private final TestresultService testresultService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, ExperienceService experienceService, TestresultService testresultService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.experienceService = experienceService;
        this.testresultService = testresultService;
    }

    /**
     * Авторизация действующего пользователя
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/api/v1/auth")
    public ResponseEntity auth(@RequestParam(name = "email") String email,
                                  @RequestParam(name = "password") String password) {

            Map<Object, Object> response = new HashMap<>();
            User user = userService.findByEmail(email);
            boolean isPasswordCorrect = false;
            if(user!=null){
                isPasswordCorrect = new BCryptPasswordEncoder().matches(password, user.getPassword());
                log.info("isPasswordCorrect" + isPasswordCorrect +"password "+password + "user.getPassword()"+ user.getPassword());
            }

            if (user == null) {
                response.put("codeResponse", 404);
                response.put("message", "Пользователь с такими email не найден");
                log.info(response.toString());
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            } else if (!isPasswordCorrect) {
                response.put("codeResponse", 401);
                response.put("message", "Неверный пароль");
                log.info(response.toString());
                return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
            } else {
                String token = jwtTokenProvider.createToken(email, user.getRoles());
                response.put("id", user.getId());
                response.put("token", token);
                response.put("message", "Пользователь найден");
                response.put("codeResponse", 201);
                response.put("user", user);
                response.put("experience", experienceService.findByUserId(user.getId()));
                response.put("testResult", testresultService.findByUserId(user.getId()));
                log.info(response.toString());
                return new ResponseEntity(response, HttpStatus.FOUND);
            }
        }
    }