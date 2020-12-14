package com.equp.back.backend.controller;

import com.equp.back.backend.model.*;
import com.equp.back.backend.service.MoodService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONObject;
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
    public ResponseEntity<?> setMood(@RequestBody JSONObject jsonObject){

        long idUser = (long) jsonObject.get("idUser");
        long[] listDates = (long[]) jsonObject.get("date");
        int[] listMoods = (int[]) jsonObject.get("mood");
        int[] listEmotions = (int[]) jsonObject.get("emotion");
        String[] listTexts = (String[]) jsonObject.get("text");

        List<Mood> moodList = new ArrayList<>();

        try {
            for (int i = 0; i < listDates.length; i++){
                moodList.add(new Mood(idUser, listDates[i],listMoods[i],listEmotions[i], listTexts[i]));
            }
            moodService.createList(moodList);

        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getStackTrace());
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }

        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/v1/getmood")
    public ResponseEntity<?> getMood(@RequestParam(value = "id", defaultValue = "-1") Long id,
                                     @RequestParam(value = "token", defaultValue = "-1") String token){

        String sql = "From " + Mood.class.getSimpleName();
        System.out.println("sql = " + sql + "where id = "+ id);

        SessionFactory sessionFactory = null;
        Session session = sessionFactory.openSession();

        List<Mood> moods = session.createQuery(sql).list();

        long idUser = id;
        List<Long> listDates = null;
        List<Integer> listMoods = null;
        List<Integer> listEmotions = null;
        List<String> listTexts = null;

        for (Mood m: moods){
            listDates.add(m.getDate());
            listMoods.add(m.getMood());
            listEmotions.add(m.getEmotion());
            listTexts.add(m.getText());

        }

        JSONObject jsonObject = new JSONObject();


        jsonObject.put("id", id);
        jsonObject.put("date", listDates.toString());
        jsonObject.put("mood", listMoods.toString());
        jsonObject.put("emotion", listEmotions.toString());
        jsonObject.put("text", listTexts);
        log.info(jsonObject.toString());

        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.ACCEPTED);

    }


}
