package com.equp.back.backend.repository;

import com.equp.back.backend.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoodRepository extends JpaRepository<Mood, Long> {
    Mood findById(long id);

    @Query("select date, mood, emotion, text from moods where id_user like :idUser")
    List<Mood> findByIdUser(@Param("idUser") long id);


}
