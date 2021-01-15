
package com.equp.back.backend.service;

import com.equp.back.backend.model.Mood;

import java.util.List;

public interface MoodService {
    void create(Mood mood);

    Mood findMood(Long id);

    boolean updateMood(Mood mood);

    boolean delete(Mood mood);

    List<Mood> readAll(long id);

}