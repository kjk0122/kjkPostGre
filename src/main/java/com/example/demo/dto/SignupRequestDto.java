package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {
    @NotBlank(message = "사용자 이름은 필수값입니다.")
    private String username;

    @NotBlank(message = "비밀번호값은 필수값입니다.")
    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$", message = "비밀번호는 영문 대,소문자와 숫자,적어도 1개 이상씩 포함된 8 ~ 15자의 비밀번호여야 합니다.")
    private String password;

    private String nickname;
}