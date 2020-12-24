package com.equp.back.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Slf4j
@Data
public class User {

    @Id

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;


//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "moods_user",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "mood_id", referencedColumnName = "id")})
//    private List<Mood> moodList;

    public User(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.password = password;
        new Experience(this.id);
    }

    public User() {
    }



}
