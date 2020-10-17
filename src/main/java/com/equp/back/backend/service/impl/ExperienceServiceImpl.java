package com.equp.back.backend.service.impl;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;
import com.equp.back.backend.repository.ExperienceRepository;
import com.equp.back.backend.service.ExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public void create(Experience experience) {
        experienceRepository.save(experience);
    }

    @Override
    public Experience findByUserId(Long id){
        return experienceRepository.findByUserId(id);
    }

    @Override
    public Experience readLast(Long id) {
        return experienceRepository.findByUserId(id);
    }

    @Override
    public boolean delete(Long id) {
        if (experienceRepository.existsById(id)) {
            experienceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Experience experience) {
        if (experienceRepository.existsById(experience.getUserId())) {
            experienceRepository.save(experience);
            return true;
        }
        return false;
    }
}
