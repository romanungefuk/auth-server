package com.equp.back.backend.service;

import com.equp.back.backend.model.Mood;

import java.util.List;

public interface MoodService {
    void create(Mood mood);

    void createList(List<Mood> moods);

    List<Mood> findMoods(long id);

    List<Mood> findByIdUser(Long idUser);

    boolean delete(Long id);

}
