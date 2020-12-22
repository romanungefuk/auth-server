package com.equp.back.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "moods")
@Slf4j
@Data
public class Mood {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "date")
    private long date;

    @Column(name = "mood")
    private int mood;

    @Column(name = "emotion")
    private int emotion;

    @Column(name = "text")
    private String text;

    public Mood() {
    }


}
