package com.equp.back.backend.repository;

import com.equp.back.backend.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Experience findByUserId(Long id);
}
