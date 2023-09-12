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
import java.util.*;

@Service
@RequiredArgsConstructor
public class YaguService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String game(@RequestBody NumberSubmitDto numberSubmitDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        return yagu(numberSubmitDto.getNumber(), user);
    }

    public String yagu(String input, User user) {
        int round = user.getCount();
        if (round == 1) {
            user.setQuizNo(generateNo());
        }
        String answer = user.getQuizNo();
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
                user.setLog(user.getCount(),"W");
                user.setCount(1);
                return "정답입니다!축하드립니다.\n 현재 당신의 승률은: " +  " % 입니다.";
            } else if (S != 0 || B != 0) {
                user.setCount(user.getCount() + 1);
                return "스트라이크 갯수: " + S + " 볼 갯수: " + B + " 현재 라운드: " + round;
            } else {
                user.setCount(user.getCount() + 1);
                return "아웃! 현재 라운드: " + round;
            }
        } else {
            user.setLog(11,"L");
            user.setCount(1);
            return "게임이 종료되었습니다. 정답은" + user.getQuizNo() + " 였습니다.\n 현재 당신의 승률은: " + " % 입니다.";
        }
    }

    public String generateNo() {
        List<Integer> nums = new ArrayList<>();
        // 0부터 9까지의 숫자를 리스트에 추가
        for (int i = 0; i < 10; i++) {
            nums.add(i);
        }
        // 리스트를 무작위로 섞음
        Collections.shuffle(nums);
        // 리스트에서 앞 4개의 숫자를 가져와서 4자리 숫자를 생성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(nums.get(i));
        }
        return sb.toString();
    }

//    public int Search(){
//
//    }

}