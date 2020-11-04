package com.equp.back.backend.service.impl;

import com.equp.back.backend.model.TestResult;
import com.equp.back.backend.repository.TestresultRepository;
import com.equp.back.backend.service.TestresultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestresultServiceImpl implements TestresultService {

    @Autowired
    private TestresultRepository testresultRepository;

    @Override
    public void create(TestResult testresult) {
        testresultRepository.save(testresult);
    }

    @Override
    public TestResult findByUserId(Long id) {
        return testresultRepository.findByUserId(id);
    }

    @Override
    public TestResult readLast(Long id) {
        return testresultRepository.findByUserId(id);
    }

    @Override
    public boolean delete(Long id) {
        if(testresultRepository.existsById(id)){
            testresultRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(TestResult testresult) {
        if (testresultRepository.existsById(testresult.getUserId())) {
            testresultRepository.save(testresult);
            return true;
        }
        return false;
    }
}
