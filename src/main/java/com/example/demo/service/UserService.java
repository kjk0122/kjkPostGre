package com.example.demo.service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(signupRequestDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        String userPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        //등록등록
        User user = new User(signupRequestDto.getUsername(), userPassword, signupRequestDto.getNickname());
        userRepository.save(user);
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
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //토큰을 생성해서 유저에게 줌
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }
    @Transactional
    public void deleteUser(Long id) {
            userRepository.deleteById(id);
    }
}