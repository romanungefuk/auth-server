package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Id;

public class MoodJsonParser {


    private Long idUser;
    long[] date;
    int[] mood;
    int[] emotion;
    String[] text;

    public MoodJsonParser() {
    }

    public MoodJsonParser(Long idUser, long[] date, int[] mood, int[] emotion, String[] text) {

        this.idUser = idUser;
        this.date = date;
        this.mood = mood;
        this.emotion = emotion;
        this.text = text;
    }



    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public long[] getDate() {
        return date;
    }

    public void setDate(long[] date) {
        this.date = date;
    }

    public int[] getMood() {
        return mood;
    }

    public void setMood(int[] mood) {
        this.mood = mood;
    }

    public int[] getEmotion() {
        return emotion;
    }

    public void setEmotion(int[] emotion) {
        this.emotion = emotion;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}

