package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moods")

public class Mood {

    @JsonIgnore
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_user")
    private Long idUser;


    @Column(name = "date")
    long date;

    @Column(name = "mood")
    int mood;

    @Column(name = "emotion")
    int emotion;

    @Column(name = "text")
    String text;

    public Mood() {
    }

    public Mood(Long idUser, long date, int mood, int emotion, String text) {

        this.idUser = idUser;
        this.date = date;
        this.mood = mood;
        this.emotion = emotion;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getEmotion() {
        return emotion;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
