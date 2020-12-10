package com.equp.back.backend.service;

import com.equp.back.backend.model.Mood;
import com.equp.back.backend.model.User;

import java.util.List;

public interface MoodService {

    void createList(List<Mood> moods);

    List<Mood> findMoods(long id);

    List<Mood> findByIdUser(Long idUser);

    boolean delete(Long id);

}
