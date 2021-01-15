package com.equp.back.backend.controller;

import com.equp.back.backend.model.Mood;
import com.equp.back.backend.service.MoodService;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mood")
@Slf4j
public class MoodController {

    private final UserService userService;
    private final MoodService moodService;

    @Autowired
    public MoodController(UserService userService, MoodService moodService) {
        this.userService = userService;
        this.moodService = moodService;
    }

    @PostMapping
    public ResponseEntity<?> setMood(@RequestBody List<Mood> moods){



        try {
            for (Mood mood: moods){
                moodService.create(mood);
            }
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }

        return new ResponseEntity<>(moods, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<?> getMood(@RequestParam(value = "id", defaultValue = "-1") Long id){


//        List<Mood> moodList = null;
//        try {
//
//            moodList = new user.getMoodList();
//        }catch (Exception e){
//            System.out.println("пользователь с id " + id + "не найден");
//            System.out.println(e.getStackTrace());
//        }

        return new ResponseEntity<>(moodService.readAll(id), HttpStatus.ACCEPTED);

    }


}