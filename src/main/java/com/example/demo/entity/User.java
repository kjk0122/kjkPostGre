package com.example.demo.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


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
//    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$", message = "비밀번호는 영문 대,소문자와 숫자,적어도 1개 이상씩 포함된 8 ~ 15자의 비밀번호여야 합니다.")
    private String password;

    @Column
    private String sex;

    @Column
    private int count;

    @Column(nullable = false)
    private Float rate;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.count = 0;
        this.rate = 0f;
    }

}
