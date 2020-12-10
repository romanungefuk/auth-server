package com.equp.back.backend.repository;

import com.equp.back.backend.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood, Long> {
    Mood findByIdUser(long id);
}
