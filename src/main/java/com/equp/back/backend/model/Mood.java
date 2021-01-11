package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "moods")
@Slf4j
public class Mood {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_id")
    private long userId;


    @Column(name = "date")
    long date;

    @Column(name = "mood")
    byte mood;

    @Column(name = "emotion")
    byte emotion;

    @Column(name = "text")
    String text;


    public Mood( long userId, long date, byte mood, byte emotion, String text) {
        this.userId = userId;
        this.text = text;
        this.mood = mood;
        this.date = date;
        this.emotion = emotion;
    }
}
