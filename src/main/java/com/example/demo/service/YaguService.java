package com.example.demo.service;


import com.example.demo.dto.NumberSubmitDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class YaguService {
    private final UserRepository userRepository;
    String answer = "3216";
    private final JwtUtil jwtUtil;

    @Transactional
    public String game(@RequestBody NumberSubmitDto numberSubmitDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user =  userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        return yagu(numberSubmitDto.getNumber(), user);
    }

    public String yagu(String input, User user) {
        int round=user.getCount();
        if (round < 10) {
            int S = 0;
            int B = 0;
            for (int i = 0; i < 4; i++) {
                String a = String.valueOf(input.charAt(i));
                if (answer.charAt(i) == input.charAt(i)) {
                    S++;
                } else if (answer.contains(a)) {
                    B++;
                }
            }
            if (S == 4) {
                user.setCount(1);
                return "정답!";
            } else if (S != 0 || B != 0) {
                user.setCount(user.getCount()+1);
                return "스트라이크 갯수: " + S + " 볼 갯수: " + B + " 현재 라운드: " + round;
            } else {
                user.setCount(user.getCount()+1);
                return "아웃! 현재 라운드: " + round;
            }
        } else {
            user.setCount(1);
            return "게임이 종료되었습니다. 다시 리셋합니다.";
        }
    }
}