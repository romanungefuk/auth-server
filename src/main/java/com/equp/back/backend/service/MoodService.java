package com.equp.back.backend.service;

import com.equp.back.backend.model.Mood;

public interface MoodService {
    void create(Mood mood);

    Mood findMood(Long id);

    boolean updateMood(Mood mood);

    boolean delete(Mood mood);

}
