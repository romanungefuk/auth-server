package com.equp.back.backend.repository;

import com.equp.back.backend.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestresultRepository extends JpaRepository<TestResult, Long> {
    TestResult findByUserId(Long id);
}
