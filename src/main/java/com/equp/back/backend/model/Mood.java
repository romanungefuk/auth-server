package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moods")
@Slf4j
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

    public Mood(Long id, Long idUser, long date, int mood, int emotion, String text) {
        this.id = id;
        this.idUser = idUser;
        this.date = date;
        this.mood = mood;
        this.emotion = emotion;
        this.text = text;
    }

}
