package com.equp.back.backend.service;

import com.equp.back.backend.model.Mood;

import java.util.List;

public interface MoodService {
    void create(Mood mood);

    void createList(List<Mood> moods);

    List<Mood> findMoods(long id);

    boolean delete(Long id);

}
