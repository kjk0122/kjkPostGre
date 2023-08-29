package com.example.demo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String sex;

    @Column(nullable = false)
    private Float rate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.rate=0f;
    }

}
