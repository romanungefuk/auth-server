package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Table(name = "experiences")
@Slf4j
public class Experience {

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

    public Experience(Long userId) {
        this.userId = userId;
        this.mindfulness = 0.0;
        this.attitudes = 0.0;
        this.selfRegulation = 0.0;
        this.empathy = 0.0;
    }

    public Experience() {

    }

    public Experience(Long userId, double allExperience, double mindfulness,
                      double attitudes, double selfRegulation, double empathy) {
        this.userId = userId;
        this.mindfulness = mindfulness;
        this.attitudes = attitudes;
        this.selfRegulation = selfRegulation;
        this.empathy = empathy;
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
}

