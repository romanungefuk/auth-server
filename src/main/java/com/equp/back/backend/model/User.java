package com.equp.back.backend.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Experience experience;

   // private Mood mood;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

//    public Mood getMood() {
//        return mood;
//    }
//
//    public void setMood(Mood mood) {
//        this.mood = mood;
//    }
}
