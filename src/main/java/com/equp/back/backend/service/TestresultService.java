package com.equp.back.backend.service;


import com.equp.back.backend.model.TestResult;

public interface TestresultService {
    void create(TestResult testresult);

    TestResult findByUserId(Long id);

    TestResult readLast(Long id);
    boolean delete(Long id);
    boolean update(TestResult testresult);

}
