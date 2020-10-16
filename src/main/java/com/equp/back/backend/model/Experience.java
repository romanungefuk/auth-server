package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Experience {
    @JsonIgnore
    private int userId;
    private double allExperience;
    private double mindfulness;
    private double attitudes;
    private double selfRegulation;
    private double empathy;

    public Experience(int userId) {
        this.userId = userId;
        this.allExperience = 0.0;
        this.mindfulness = 0.0;
        this.attitudes = 0.0;
        this.selfRegulation = 0.0;
        this.empathy = 0.0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAllExperience() {
        return allExperience;
    }

    public void setAllExperience(double allExperience) {
        this.allExperience = allExperience;
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

