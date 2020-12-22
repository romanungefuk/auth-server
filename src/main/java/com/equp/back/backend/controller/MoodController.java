package com.equp.back.backend.controller;

import com.equp.back.backend.model.Mood;
import com.equp.back.backend.model.User;
import com.equp.back.backend.service.MoodService;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class MoodController {

    private final UserService userService;
    private final MoodService moodService;

    @Autowired
    public MoodController(UserService userService, MoodService moodService) {
        this.userService = userService;
        this.moodService = moodService;
    }

    @PostMapping(value = "/api/v1/setmood")
    public ResponseEntity<?> setMood(@RequestBody List<Mood> moodList){


        try {
            for (Mood mood : moodList){
                moodService.create(mood);
                log.info("создан " + mood);
            }
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }

        return new ResponseEntity<>(moodList, HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/v1/getmood")
    public ResponseEntity<?> getMood(@RequestParam(value = "id", defaultValue = "-1") Long id,
                                     @RequestParam(value = "token", defaultValue = "-1") String token){

        User user = null;
        try {
            user = userService.findById(id);
        }catch (Exception e){
            System.out.println("пользователь с id " + id + "не найден");
            System.out.println(e.getStackTrace());
        }

        return new ResponseEntity<>(user.getMoodList(), HttpStatus.ACCEPTED);

    }


}
