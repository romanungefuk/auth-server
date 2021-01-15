package com.equp.back.backend.service.impl;

import com.equp.back.backend.exception.MoodNotFoundException;
import com.equp.back.backend.model.Mood;
import com.equp.back.backend.repository.MoodRepository;
import com.equp.back.backend.service.MoodService;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MoodServiceImpl implements MoodService {
    private MoodRepository moodRepository;
    private UserService userService;

    @Autowired
    public void setMoodRepository(MoodRepository moodRepository, UserService userService){
        this.moodRepository = moodRepository;
        this.userService = userService;
    }


    public Mood saveOrUpdate(Mood mood){
        return moodRepository.save(mood);
    }

    @Override
    public void create(Mood mood) {

        moodRepository.save(mood);
    }


    public Mood findMood(Long id) {
        return moodRepository.findById(id).orElseThrow(() -> new MoodNotFoundException("не найдены записи об настроении с id = " + id));
    }

    @Override
    public boolean updateMood(Mood mood) {
        return false;
    }

    @Override
    public boolean delete(Mood mood) {

        boolean result = false;
        try {
            moodRepository.delete(mood);
            result = true;
        }catch (Exception e){
            System.out.println("ошибка при удалении настроения");
            System.out.println(e.getStackTrace());
        }
        return result;
    }

    @Override
    public List<Mood> readAll(long id) {

        List<Mood> result = moodRepository.findAll();

        for (int i = 0; i < result.size(); i++){
            if (result.get(i).getUserId() != id) {
                result.remove(result.get(i));
                if (i > 0) i--;
            }

        }

        return result;
    }
}