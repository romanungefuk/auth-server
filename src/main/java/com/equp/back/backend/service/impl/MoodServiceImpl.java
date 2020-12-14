package com.equp.back.backend.service.impl;

import com.equp.back.backend.model.Mood;
import com.equp.back.backend.repository.MoodRepository;
import com.equp.back.backend.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoodServiceImpl implements MoodService {

    @Autowired
    private MoodRepository moodRepository;

    @Override
    public void create(Mood mood) {

    }

    @Override
    public void createList(List<Mood> moods) {
        List<Mood> moodList = new ArrayList<>();

        for (Mood m : moods){
         moodRepository.save(m);
        }
    }

    @Override
    public List<Mood> findMoods(long id) {
        return null;
    }

    @Override
    public List<Mood> findByIdUser(Long idUser) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }


}
