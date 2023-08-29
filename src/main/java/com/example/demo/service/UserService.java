package com.example.demo.service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    String pt = "^[a-z\\\\d`~!@#$%^&*()-_=+]{4,10}$";
    String ptt = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$";

    @Transactional
    public User signup(SignupRequestDto signupRequestDto) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String username = signupRequestDto.getUsername();
        String pwcheck = signupRequestDto.getPassword();

        //username 확인
        if (!Pattern.matches(pt, username)) {
            throw new IllegalArgumentException(
                    "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 되어야합니다.");
        }
        //비밀번호 확인
        if (!Pattern.matches(ptt, pwcheck)) {
            throw new IllegalArgumentException(
                    "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 되어야합니다.");
        }
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        //저장을 바로 하면 안되고 encoding해서 저장하기
        String password = pwcheck;
        //등록등록
        User user = new User(username, password);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if (password != user.getPassword()) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
    @Transactional
    public void deleteUser(Long id) {
            userRepository.deleteById(id);
    }
}