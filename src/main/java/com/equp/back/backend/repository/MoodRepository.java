package com.equp.back.backend.repository;

import com.equp.back.backend.model.Mood;
import com.equp.back.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> {

}
