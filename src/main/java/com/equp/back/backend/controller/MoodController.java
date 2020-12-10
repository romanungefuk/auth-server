package com.equp.back.backend.controller;

import com.equp.back.backend.model.*;
import com.equp.back.backend.service.MoodService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MoodController {

    private final MoodService moodService;


    @Autowired
    public MoodController(MoodService moodService) {
        this.moodService = moodService;
    }

    @PostMapping(value = "/api/v1/setmood")
    public ResponseEntity<?> setMood(@RequestBody JSONObject jsonObject){
        long idUser = (long)  jsonObject.get("idUser");
    }


}
