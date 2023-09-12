package com.example.demo.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
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
//    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$", message = "비밀번호는 영문 대,소문자와 숫자,적어도 1개 이상씩 포함된 8 ~ 15자의 비밀번호여야 합니다.")
    private String password;

    @Column
    private String quizNo = "0000";
    @Column
    private int count = 1;
    @ElementCollection//게임 기록들을 남김.
    @CollectionTable(name = "user_log")
    @MapKeyColumn(name = "Gameplay_history")
    private Map<Integer, String> log = new HashMap<>();

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public void setLog(int i, String s) {
        this.log.put(i,s);
    }
}
