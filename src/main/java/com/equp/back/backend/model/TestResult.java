package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testresult")
@Slf4j
public class TestResult {

    @JsonIgnore
    @Id
    @Column(name = "id")

    private Long userId;

    @Column(name = "mindfulness")
    private double mindfulness;

    @Column(name = "attitudes")
    private double attitudes;

    @Column(name = "selfregulation")
    private double selfRegulation;

    @Column(name = "empathy")
    private double empathy;

    public TestResult(Long userId) {
        this.userId = userId;
        this.mindfulness = mindfulness;
        this.attitudes = attitudes;
        this.selfRegulation = selfRegulation;
        this.empathy = empathy;
    }

    public TestResult() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getMindfulness() {
        return mindfulness;
    }

    public void setMindfulness(double mindfulness) {
        this.mindfulness = mindfulness;
    }

    public double getAttitudes() {
        return attitudes;
    }

    public void setAttitudes(double attitudes) {
        this.attitudes = attitudes;
    }

    public double getSelfRegulation() {
        return selfRegulation;
    }

    public void setSelfRegulation(double selfRegulation) {
        this.selfRegulation = selfRegulation;
    }

    public double getEmpathy() {
        return empathy;
    }

    public void setEmpathy(double empathy) {
        this.empathy = empathy;
    }

    public static Logger getLog() {
        return log;
    }
}
