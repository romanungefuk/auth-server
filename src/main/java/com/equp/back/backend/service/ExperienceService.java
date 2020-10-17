package com.equp.back.backend.service;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;

public interface ExperienceService {
    void create(Experience experience);

    Experience findByUserId(Long id);

    Experience readLast(Long id);
    boolean delete(Long id);
    boolean update(Experience experience);

}
