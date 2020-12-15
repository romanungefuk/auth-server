package com.equp.back.backend.controller;

import com.equp.back.backend.model.*;
import com.equp.back.backend.service.MoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class MoodController {

    private final MoodService moodService;


    @Autowired
    public MoodController(MoodService moodService) {
        this.moodService = moodService;
    }


    @PostMapping(value = "/api/v1/setmood")
    public ResponseEntity<?> setMood(@RequestBody MoodJsonParser moodJsonParser){

        List<Mood> moodList = new ArrayList<>();

        try {
            for (int i = 0; i < moodJsonParser.getMood().length; i++){
                moodList.add(new Mood(moodJsonParser.getIdUser(), moodJsonParser.getDate()[i],moodJsonParser.getMood()[i],moodJsonParser.getEmotion()[i], moodJsonParser.getText()[i]));

            }
            moodService.createList(moodList);

        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getStackTrace());
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }

        ;

        for(int i = 0; i < moodList.size(); i++){

        }

        return new ResponseEntity<>(moodJsonParser, HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/v1/getmood")
    public ResponseEntity<?> getMood(@RequestParam(value = "id", defaultValue = "-1") Long id,
                                     @RequestParam(value = "token", defaultValue = "-1") String token){

        for (Mood m: moodService.findByIdUser(id)){
            System.out.println(m.getText());
        }


        return new ResponseEntity<>(2, HttpStatus.ACCEPTED);

    }


}
