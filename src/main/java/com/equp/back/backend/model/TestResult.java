package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_result")
@Slf4j
public class TestResult {

    @JsonIgnore
    @Id
    @Column(name = "id")
    private Long userId;

    @Column(name = "start_location")
    private double startLocation;

    @Column(name = "mindfulness")
    private double mindfulness;

    @Column(name = "attitudes")
    private double attitudes;

    @Column(name = "selfregulation")
    private double selfregulation;

    @Column(name = "empathy")
    private double empathy;

    public TestResult(Long userId) {
        this.userId = userId;
        this.mindfulness = 0.0;
        this.attitudes = 0.0;
        this.selfregulation = 0.0;
        this.empathy = 0.0;
        this.startLocation = 0.0;
    }

    public TestResult() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(double startLocation) {
        this.startLocation = startLocation;
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

    public double getSelfregulation() {
        return selfregulation;
    }

    public void setSelfregulation(double selfRegulation) {
        this.selfregulation = selfRegulation;
    }

    public double getEmpathy() {
        return empathy;
    }

    public void setEmpathy(double empathy) {
        this.empathy = empathy;
    }
}
