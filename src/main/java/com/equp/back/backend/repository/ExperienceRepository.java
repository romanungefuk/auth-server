package com.equp.back.backend.repository;

import com.equp.back.backend.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Experience findByUserId(Long id);
}
