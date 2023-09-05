package com.example.demo.service;


import com.example.demo.dto.NumberSubmitDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class YaguService {
    private final UserRepository userRepository;
    String answer = "3216";
    int round = 0;

    @Transactional
    public String game(@RequestBody NumberSubmitDto numberSubmitDto) {
        // 사용자 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
//        );
        return yagu(numberSubmitDto.getNumber(), round);
    }

    public String yagu(String input, int round) {
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
                round = 0;
                return "정답!";
            } else if (S != 0 || B != 0) {
                round++;
                return "스트라이크 갯수: " + S + " 볼 갯수: " + B + " 현재 라운드: " + round;
            } else {
                round++;
                return "아웃! 현재 라운드: " + round;
            }
        } else {
            round = 0;
            return "게임이 종료되었습니다.";
        }
    }
}